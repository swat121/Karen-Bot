package com.project.karenbot.handler.button.inline;

import com.project.karenbot.dto.board.Data;
import com.project.karenbot.dto.board.Device;
import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.BoardConfigService;
import com.project.karenbot.service.BotService;
import com.project.karenbot.service.ButtonService;
import com.project.karenbot.service.CommonService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.project.karenbot.enums.Tags.ACTION_TAG;
import static com.project.karenbot.enums.Tags.MODULE_TAG;

@Component
@AllArgsConstructor
public class ModuleButtonHandler extends AbstractMessageHandler {

    private final ButtonService buttonService;

    private final CommonService commonService;

    private ApplicationContext applicationContext;

    private BotService getBotService() {
        return applicationContext.getBean(BotService.class);
    }


    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasCallbackQuery()
                && Arrays.stream(update.getCallbackQuery().getData().split("_")).anyMatch((el) -> el.equalsIgnoreCase(MODULE_TAG.getTag()))
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        HashMap<String, String> moduleData = commonService.parseModuleCallbackData(update.getCallbackQuery().getData());

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setText("Completed");
        BotService botService = getBotService();
        botService.executeButtonCallback(answerCallbackQuery);

        return buttonService.setInlineKeyboardButton(update.getCallbackQuery().getMessage(), moduleData, "Device data");
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

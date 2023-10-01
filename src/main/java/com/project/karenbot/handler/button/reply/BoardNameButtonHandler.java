package com.project.karenbot.handler.button.reply;

import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.BoardConfigService;
import com.project.karenbot.service.ButtonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
@AllArgsConstructor
public class BoardNameButtonHandler extends AbstractMessageHandler {
    private final ButtonService buttonService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && BoardConfigService.parseBoardsNames().contains(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        HashMap<String, String> settingNames = new HashMap<>();
        String boardName = update.getMessage().getText();
        settingNames.put("Sensors", boardName + "_sensors");
        settingNames.put("Trackers", boardName + "_trackers");
        settingNames.put("Switchers", boardName + "_switchers");
        return buttonService.setInlineKeyboardButton(update.getMessage(), settingNames);
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}
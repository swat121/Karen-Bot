package com.project.karenbot.handler.button.inline;

import com.project.karenbot.dto.board.Data;
import com.project.karenbot.dto.board.Device;
import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.BoardConfigService;
import com.project.karenbot.service.ButtonService;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.project.karenbot.enums.Services.KAREN;
import static com.project.karenbot.enums.Tags.ACTION_TAG;
import static com.project.karenbot.enums.Tags.MODULE_TAG;

@Component
@AllArgsConstructor
public class ActionButtonHandler extends AbstractMessageHandler {

    private final ConnectionService connectionService;


    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasCallbackQuery()
                && Arrays.stream(update.getCallbackQuery().getData().split("_")).anyMatch((el) -> el.equalsIgnoreCase(ACTION_TAG.getTag()))
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        String[] callbackData = update.getCallbackQuery().getData().split("_");
        String boardName = callbackData[0];
        String settingName = callbackData[1];
        String moduleName = callbackData[2];
        String moduleId = callbackData[3];

        String url = String.format("/api/v1/%s/%s/%s/%s", boardName, settingName, moduleName, moduleId);

        String response = switch (settingName) {
            case "switchers" -> connectionService.putRequestForService(KAREN.name(), url, null);
            case "sensors" -> connectionService.getResponseFromService(KAREN.name(), url, String.class);
            default -> "Setting not found";
        };

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        sendMessage.setText(response);
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}
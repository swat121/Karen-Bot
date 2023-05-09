package com.project.karenbot.handler.command;

import com.project.karenbot.config.DataConfig;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import com.project.karenbot.model.DataResponse;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Commands.*;
import static com.project.karenbot.enums.Services.KAREN;

@Component
@AllArgsConstructor
public class StatusCommandHandler extends AbstractMessageHandler {

    private final ConnectionService connectionService;
    private final DataConfig dataConfig;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && STATUS.getCommand().equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        DataResponse dataResponse;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        dataResponse = connectionService.getResponseFromService(KAREN.getName(), "/patric/status", DataResponse.class);
        String temp = connectionService.getResponseFromService(KAREN.getName(), "/patric/sensor/temperature", String.class);
        sendMessage.setText(
                "Name: " + dataResponse.getName() + "\n" +
                        MAIN_LIGHT.getCommand() + ": " + dataConfig.getRelayS().get(dataResponse.getRelay1()) + "\n" +
                        BACK_LIGHT.getCommand() + ": " + dataConfig.getLightS().get(dataResponse.getLight()) + "\n" +
                        OPEN + ": " + dataConfig.getRelayS().get(dataResponse.getRelay2()) + "\n" +
                        "Temperature: " + temp);
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

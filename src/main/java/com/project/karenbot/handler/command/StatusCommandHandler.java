package com.project.karenbot.handler.command;

import com.project.karenbot.config.DataConfig;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.model.DataResponse;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class StatusCommandHandler extends AbstractMessageHandler {

    private final ConnectionService connectionService;
    private final DataConfig dataConfig;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "Status".equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        DataResponse dataResponse;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        dataResponse = connectionService.getDataResponse("Patric", "/status");
        sendMessage.setText(
                "Name: " + dataResponse.getName() + "\n" +
                "Main light: " + dataConfig.getRelayS().get(dataResponse.getRelay1()) + "\n" +
                "Back light: " + dataConfig.getLightS().get(dataResponse.getLight()) + "\n" +
                "Lock: " + dataConfig.getRelayS().get(dataResponse.getRelay2()));
        return sendMessage;
    }
}

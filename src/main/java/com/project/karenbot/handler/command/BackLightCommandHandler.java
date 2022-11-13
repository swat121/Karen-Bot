package com.project.karenbot.handler.command;

import com.project.karenbot.config.DataConfig;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class BackLightCommandHandler extends AbstractMessageHandler {

    private final DataConfig dataConfig;
    private final ConnectionService connectionService;


    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "Back Light".equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(dataConfig.getLightS().get(connectionService.getResponse("karen", "/patric/sensor/light")));
        return sendMessage;
    }
}

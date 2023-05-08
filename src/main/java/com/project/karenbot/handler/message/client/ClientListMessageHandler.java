package com.project.karenbot.handler.message.client;

import com.project.karenbot.dto.Client;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.handler.Types;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class ClientListMessageHandler extends AbstractMessageHandler {
    private final ConnectionService connectionService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "/clients".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(getClients().toString());
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }

    private List<Client> getClients() {
        return List.of(connectionService.getObjectFromService("karen-data", "/clients", Client[].class));
    }
}

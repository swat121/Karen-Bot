package com.project.karenbot.handler.message.client;

import com.project.karenbot.dto.Client;
import com.project.karenbot.enums.Messages;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.project.karenbot.enums.Messages.CLIENTS;
import static com.project.karenbot.enums.Services.KAREN_DATA;

@Component
@AllArgsConstructor
public class ClientListMessageHandler extends AbstractMessageHandler {
    private final ConnectionService connectionService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && CLIENTS.getMessage().equals(update.getMessage().getText())
                && user;
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
        return List.of(connectionService.getObjectFromService(KAREN_DATA.getName(), "/clients", Client[].class));
    }
}

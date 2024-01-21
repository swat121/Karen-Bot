package com.project.karenbot.handler.message.client;

import com.project.karenbot.enums.Messages;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Messages.DELETE;
import static com.project.karenbot.enums.Services.KAREN_DATA;

@Component
@AllArgsConstructor
public class DeleteListMessageHandler extends AbstractMessageHandler {
    private final ConnectionService connectionService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && DELETE.getMessage().equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        connectionService.delete(KAREN_DATA.getTitle(), "/api/v1/clients");
        sendMessage.setText("All client deleted");
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

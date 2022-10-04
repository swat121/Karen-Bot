package com.project.karenbot.handler.message;

import com.project.karenbot.handler.AbstractMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IdMessageHandler extends AbstractMessageHandler {

    @Override
    public boolean canHandler(Update update) {
        return update.hasMessage()
                && "/id".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handlMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(update.getMessage().getChatId().toString());
        return sendMessage;
    }
}

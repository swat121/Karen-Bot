package com.project.karenbot.handler.message;

import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.handler.Types;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IdMessageHandler extends AbstractMessageHandler {

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "/id".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(update.getMessage().getChatId().toString());
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

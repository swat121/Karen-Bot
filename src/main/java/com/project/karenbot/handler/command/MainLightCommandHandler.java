package com.project.karenbot.handler.command;

import com.project.karenbot.handler.AbstractMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainLightCommandHandler extends AbstractMessageHandler {
    @Override
    public boolean canHandler(Update update) {
        return update.hasMessage()
                && "Main Light".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handlMessage(Update update) {

        return null;
    }
}

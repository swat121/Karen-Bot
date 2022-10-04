package com.project.karenbot.handler;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public abstract class AbstractMessageHandler {

    abstract public boolean canHandler(Update update);

    abstract public SendMessage handlMessage(Update update);
}

package com.project.karenbot.handler;

import com.project.karenbot.enums.Types;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public abstract class AbstractMessageHandler {

    abstract public boolean canHandle(Update update, boolean user);

    abstract public <T> T handleMessage(Update update);
    abstract public Types getTypeOfMethod();
}

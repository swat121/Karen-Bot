package com.project.karenbot.handler.button.inline;

import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class ModuleButtonHandler extends AbstractMessageHandler {
    @Override
    public boolean canHandle(Update update, boolean user) {
        return false;
    }

    @Override
    public <T> T handleMessage(Update update) {
        return null;
    }

    @Override
    public Types getTypeOfMethod() {
        return null;
    }
}

package com.project.karenbot.handler.message;


import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import com.project.karenbot.service.ButtonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Commands.*;
import static com.project.karenbot.enums.Messages.START;

@Component
@AllArgsConstructor
public class StartMessageHandler extends AbstractMessageHandler {

    private final ButtonService buttonService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && START.getMessage().equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        return buttonService.sendMsgForButton(update.getMessage(), new String[]{MAIN_LIGHT.getCommand(), BACK_LIGHT.getCommand()}, new String[]{OPEN.getCommand(), STATUS.getCommand()});
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

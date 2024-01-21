package com.project.karenbot.handler.message;


import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import com.project.karenbot.service.BoardConfigService;
import com.project.karenbot.service.ButtonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Messages.START;

@Component
@AllArgsConstructor
public class StartMessageHandler extends AbstractMessageHandler {

    private final ButtonService buttonService;
    private final BoardConfigService boardConfigService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && START.getMessage().equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        boardConfigService.uploadData();
        return buttonService.setReplyKeyboardButton(update.getMessage(), boardConfigService.parseBoardsNames(), "Your board");
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

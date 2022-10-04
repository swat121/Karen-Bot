package com.project.karenbot.handler.message;


import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.ButtonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class StartMessageHandler extends AbstractMessageHandler {

    private final ButtonService buttonService;

    @Override
    public boolean canHandler(Update update) {
        return update.hasMessage()
                && "/start".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handlMessage(Update update) {
        return buttonService.sendMsgForButton(update.getMessage(),new String[]{"Main light", "Back light"}, new String[]{"Open", "Status"});
    }
}

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
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "/start".equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        return buttonService.sendMsgForButton(update.getMessage(), new String[]{"Main Light", "Back Light"}, new String[]{"Open", "Status"});
    }
}

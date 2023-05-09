package com.project.karenbot.handler.message;

import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Messages.TIK;

@Component
@AllArgsConstructor
public class TikMessageHandler extends AbstractMessageHandler {

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && TIK.getMessage().equals(update.getMessage().getText());
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("tok");
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

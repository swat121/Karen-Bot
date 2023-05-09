package com.project.karenbot.handler.command;

import com.project.karenbot.config.DataConfig;
import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.project.karenbot.enums.Commands.MAIN_LIGHT;
import static com.project.karenbot.enums.Services.KAREN;

@Component
@AllArgsConstructor
public class MainLightCommandHandler extends AbstractMessageHandler {

    private final ConnectionService connectionService;
    private final DataConfig dataConfig;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && MAIN_LIGHT.getCommand().equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(dataConfig.getRelayS().get(connectionService.getResponseFromService(KAREN.getName(), "/patric/setting/relay1", String.class)));
        return sendMessage;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendMessage;
    }
}

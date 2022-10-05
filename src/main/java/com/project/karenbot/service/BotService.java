package com.project.karenbot.service;

import com.project.karenbot.config.BotConfig;
import com.project.karenbot.config.DataConfig;
import com.project.karenbot.config.UrlConfig;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.model.DataResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PreDestroy;
import java.util.*;

@Component
@AllArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final List<AbstractMessageHandler> messageHandlers;

    @PreDestroy
    public void shutdown() {
        System.out.println("stopstopstopstopstop");
    }

    @Override
    public String getBotUsername() {
        return botConfig.getId();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            if (message != null && message.hasText()) {
                Optional<AbstractMessageHandler> handler = messageHandlers.stream()
                        .filter(it -> it.canHandle(update, checkUser(message)))
                        .findFirst();
                if (handler.isPresent()) {
                    execute(handler.get().handleMessage(update));
                } else {
                    sendMsg(message, "The command is not exist or you are not in the user list");
                }
            }
        } catch (Exception e){
            sendMsg(message, e.getMessage());
        }
    }

    @SneakyThrows
    public void sendMsg(Message message, String text) {
        execute(SendMessage
                .builder()
                .chatId(message.getChatId().toString())
                .text(text)
                .build());
    }

    private boolean checkUser(Message message) {
        List<String> listOfUsers = Arrays.asList(botConfig.getUsers().split(","));
        if (listOfUsers.stream().filter(element -> (element.equals(message.getChatId().toString()))).findFirst().orElse(null) != null){
            return true;
        } else return false;
    }
}

package com.project.karenbot.service;

import com.project.karenbot.config.BotConfig;
import com.project.karenbot.dto.Notify;
import com.project.karenbot.handler.AbstractMessageHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final List<AbstractMessageHandler> messageHandlers;
    private final UserService userService;
    private static final Logger LOG = LogManager.getRootLogger();

    @PreDestroy
    public void shutdown() {
        sendMessage(Notify.builder()
                .message("Bot stopped")
                .telegramIds(userService.getUsers())
                .build());
    }

    @Override
    public String getBotUsername() {
        return botConfig.getId();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    //TODO подставлять чат айди по другому
//    @Override
//    public void onRegister() {
//        sendMessage(Notify.builder()
//                .message("Bot rebooted")
//                .telegramIds(userService.getUsers())
//                .build());
//    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            if ((message != null && message.hasText()) || update.hasCallbackQuery()) {
                Optional<AbstractMessageHandler> handler = messageHandlers.stream()
                        .filter(it -> it.canHandle(update, checkUser(update)))
                        .findFirst();
                if (handler.isPresent()) {
                    switch (handler.get().getTypeOfMethod()) {
                        case SendMessage -> execute(handler.get().<SendMessage>handleMessage(update));
                        case SendDocument -> execute(handler.get().<SendDocument>handleMessage(update));
                        case SendPhoto -> execute(handler.get().<SendPhoto>handleMessage(update));
                        case SendVideo -> execute(handler.get().<SendVideo>handleMessage(update));
                        case SendSticker -> execute(handler.get().<SendSticker>handleMessage(update));
                    }
                } else {
                    sendMessage(update, "The command is not exist or you are not in the user list");
                }
            }
        } catch (Exception e) {
            sendMessage(update, e.getMessage());
        }
    }

    @SneakyThrows
    public void sendMessage(Update update, String text) {
        Message message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        execute(SendMessage
                .builder()
                .chatId(message.getChatId().toString())
                .text(text)
                .build());
    }

    @SneakyThrows
    public void sendMessage(Notify data) {
        for (String id : data.getTelegramIds()) {
            execute(SendMessage
                    .builder()
                    .chatId(id)
                    .text(data.getMessage())
                    .build());
        }
    }

    private boolean checkUser(Update update) {
        Message message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        String chatId = message.getChatId().toString();
        return userService.getUsers().contains(chatId);
    }
}

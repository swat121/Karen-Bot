package com.project.karenbot.service;

import com.project.karenbot.config.BotConfig;
import com.project.karenbot.dto.ExternalUser;
import com.project.karenbot.dto.Notify;
import com.project.karenbot.handler.AbstractMessageHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final List<AbstractMessageHandler> messageHandlers;
    private final UserService userService;

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

    @Override
    public void onRegister() {
        sendMessage(Notify.builder()
                .message("Bot rebooted")
                .telegramIds(userService.getUsers())
                .build());
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            if (update.hasCallbackQuery()) {
            } else if (message != null && message.hasText()) {
                Optional<AbstractMessageHandler> handler = messageHandlers.stream()
                        .filter(it -> it.canHandle(update, checkUser(message)))
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
                    sendMessage(message, "The command is not exist or you are not in the user list");
                }
            }
        } catch (Exception e) {
            sendMessage(message, e.getMessage());
        }
    }

    @SneakyThrows
    public void sendMessage(Message message, String text) {
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

    private boolean checkUser(Message message) {
        String chatId = message.getChatId().toString();
        return userService.getUsers().contains(chatId);
    }
}

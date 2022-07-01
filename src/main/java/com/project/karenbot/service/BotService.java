package com.project.karenbot.service;

import com.project.karenbot.util.Karen;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BotService extends TelegramLongPollingBot {
    public Karen karen;
    @Override
    public String getBotUsername() {
        return "@wikardBot";
    }

    @Override
    public String getBotToken() {
        return "5279096564:AAFZi7VtekLt6_MrFwA7cd7zuUuj6NOd4lM";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsgForButton(message);
                    break;
                case "Temperature":
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(karen.getTemperature()).build());
                    break;
            }
        }
    }
    @SneakyThrows
    public void sendMsgForButton(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(message.getText());
        setButton(sendMessage);
        execute(sendMessage);
    }
    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();

        keyboardRowFirst.add(new KeyboardButton("Temperature"));

        keyboardRowList.add(keyboardRowFirst);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}

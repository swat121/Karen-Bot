package com.project.karenbot.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
    private final String chatID = "250412288";
    private boolean statusMessage;
    private final RestTemplate restTemplate;
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
            if (statusMessage) {
                String text = message.getText();
                String fooResourceUrl
                        = "http://localhost:8080//garry/message/";
                ResponseEntity<String> response
                        = restTemplate.getForEntity(fooResourceUrl+text, String.class);
                execute(SendMessage
                        .builder()
                        .chatId(chatID)
                        .text(response.getBody())
                        .build());
                statusMessage = false;
            } else {
                switch (message.getText()) {
                    case "/start":
                        sendMsgForButton(message, new String[]{"Garry"}, new String[]{});
                        break;
                    case "Garry":
                        sendMsgForButton(message, new String[]{"Temperature", "Backlight On", "Backlight Off"}, new String[]{"Message"});
                        break;
                    case "Temperature":
                        String fooResourceUrl
                                = "http://localhost:8080//garry/temperature";
                        ResponseEntity<String> response
                                = restTemplate.getForEntity(fooResourceUrl, String.class);
                        execute(SendMessage
                                .builder()
                                .chatId(chatID)
                                .text(response.getBody())
                                .build());
                        break;
                    case "Message":
                        execute(SendMessage
                                .builder()
                                .chatId(chatID)
                                .text("Введите текст")
                                .build());
                        statusMessage = true;
                        break;
                }
            }
        }
    }
    @SneakyThrows
    public void sendMsgForButton(Message message, String[] nameFirstButtons, String[] nameSecondButtons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(message.getText());
        setButton(sendMessage, nameFirstButtons, nameSecondButtons);
        execute(sendMessage);
    }
    public void setButton(SendMessage sendMessage, String[] nameFirstButtons, String[] nameSecondButtons){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();
        KeyboardRow keyboardRowSecond = new KeyboardRow();

        for(int i=0;i< nameFirstButtons.length;i++){
            keyboardRowFirst.add(new KeyboardButton(nameFirstButtons[i]));
        }
        for(int i=0;i< nameSecondButtons.length;i++){
            keyboardRowSecond.add(new KeyboardButton(nameSecondButtons[i]));
        }
        keyboardRowList.add(keyboardRowFirst);
        keyboardRowList.add(keyboardRowSecond);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}

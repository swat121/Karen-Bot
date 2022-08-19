package com.project.karenbot.service;

import com.project.karenbot.config.UrlConfig;
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
import java.util.Objects;

@Component
@AllArgsConstructor
public class BotService extends TelegramLongPollingBot {
    private final UrlConfig urlConfig;
    private final RestTemplate restTemplate;
    @Override
    public String getBotUsername() {
        return urlConfig.getCredential().get("idTest");
    }

    @Override
    public String getBotToken() {
        return urlConfig.getCredential().get("tokenTest");
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsgForButton(message, new String[]{"Main light","Back light"}, new String[]{"Help", "Status"});
                    break;
                case "/id":
                    sendMsg(message, message.getChatId().toString());
                    break;
                default:
                    vipCommand(message);
                    break;
            }
        }
    }
    @SneakyThrows
    public void vipCommand(Message message){
        ResponseEntity<String> response;
        if (urlConfig.getResource().get(message.getChatId()) != null) {
            switch (message.getText()){
                case "Main light":
                    sendMsg(message, "Send data");
                    response
                            = restTemplate.getForEntity(urlConfig.getResource().get("Patric") + "/setting/relay1", String.class);
                    sendMsg(message, response.getBody());
                    break;
                case "Back light":
                    sendMsg(message, "Send data");
                    response
                            = restTemplate.getForEntity(urlConfig.getResource().get("Patric") + "/setting/relay2", String.class);
                    sendMsg(message, response.getBody());
                    break;
                case "Status":
                    sendMsg(message, "Send data");
                    response
                            = restTemplate.getForEntity(urlConfig.getResource().get("Patric") + "/status", String.class);
                    sendMsg(message, response.getBody());
                    break;
                case "Help":
                    sendMsg(message, "Send data");
                    response
                            = restTemplate.getForEntity(urlConfig.getResource().get("Patric") + "/help", String.class);
                    sendMsg(message, response.getBody());
                    break;
            }
        } else {
            sendMsg(message, "Sorry, but you are not on the user list or command not found\n(you can go fuck yourself)");
        }
    }
    @SneakyThrows
    public void sendMsg(Message message, String text){
        execute(SendMessage
                .builder()
                .chatId(message.getChatId().toString())
                .text(text)
                .build());
    }
    @SneakyThrows
    public void sendMsgForButton(Message message, String[] nameFirstButtons, String[] nameSecondButtons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("You have some buttons for control home");
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

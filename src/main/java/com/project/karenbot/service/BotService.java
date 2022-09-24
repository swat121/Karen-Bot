package com.project.karenbot.service;

import com.project.karenbot.config.BotConfig;
import com.project.karenbot.config.DataConfig;
import com.project.karenbot.config.UrlConfig;
import com.project.karenbot.model.DataResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
    private final UrlConfig urlConfig;
    private final RestTemplate restTemplate;
    private final DataConfig dataConfig;
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getId();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsgForButton(message, new String[]{"Main light", "Back light"}, new String[]{"Open", "Status"});
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
    public void vipCommand(Message message) {
        DataResponse dataResponse;
        String response;
        if (botConfig.getUsers().get("user1") == message.getChatId().toString() || botConfig.getUsers().get("user2") == message.getChatId().toString()) {
            try {
                sendMsg(message, "Send: " + "'" + message.getText() + "'");
                switch (message.getText()) {
                    case "Main light":
                        response
                                = getFromESP(urlConfig.getResource().get("Patric") + "/setting/relay1", String.class);
                        sendMsg(message, dataConfig.getRelayS().get(response));
                        break;
                    case "Back light":
                        response
                                = getFromESP(urlConfig.getResource().get("Patric") + "/sensor/light", String.class);
                        sendMsg(message, dataConfig.getLightS().get(response));
                        break;
                    case "Open":
                        response
                                = getFromESP(urlConfig.getResource().get("Patric") + "/setting/relay2", String.class);
                        sendMsg(message, dataConfig.getRelayS().get(response));
                        break;
                    case "Status":
                        dataResponse
                                = getFromESP(urlConfig.getResource().get("Patric") + "/status", DataResponse.class);
                        sendMsg(message,
                                "Name: " + dataResponse.getName() + "\n" +
                                        "Main light: " + dataConfig.getRelayS().get(dataResponse.getRelay1()) + "\n" +
                                        "Back light: " + dataConfig.getLightS().get(dataResponse.getLight()) + "\n" +
                                        "Lock: " + dataConfig.getRelayS().get(dataResponse.getRelay2()));
                        break;
                }
            } catch (Exception e) {
                sendMsg(message, "Karen does not answer: " + e.getMessage());
            }
        } else {
            sendMsg(message, "Sorry, but you are not on the user list or command not found\n\n(you can go fuck yourself)");
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

    @SneakyThrows
    public void sendMsgForButton(Message message, String[] nameFirstButtons, String[] nameSecondButtons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("You have some buttons for control home (Patric)");
        setButton(sendMessage, nameFirstButtons, nameSecondButtons);
        execute(sendMessage);
    }

    public void setButton(SendMessage sendMessage, String[] nameFirstButtons, String[] nameSecondButtons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();
        KeyboardRow keyboardRowSecond = new KeyboardRow();

        for (int i = 0; i < nameFirstButtons.length; i++) {
            keyboardRowFirst.add(new KeyboardButton(nameFirstButtons[i]));
        }
        for (int i = 0; i < nameSecondButtons.length; i++) {
            keyboardRowSecond.add(new KeyboardButton(nameSecondButtons[i]));
        }
        keyboardRowList.add(keyboardRowFirst);
        keyboardRowList.add(keyboardRowSecond);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    private <T> T getFromESP(String url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType).getBody();
    }
}

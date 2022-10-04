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

    private final UrlConfig urlConfig;
    private final RestTemplate restTemplate;
    private final DataConfig dataConfig;
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
        if (message != null && message.hasText()) {
            Optional<AbstractMessageHandler> handler = messageHandlers.stream()
                    .filter(it -> it.canHandler(update))
                    .findFirst();
            if (handler.isPresent()) {
                execute(handler.get().handlMessage(update));
            } else {
                sendMsg(message, "The command is not exist");
            }
        }
    }

    @SneakyThrows
    public void vipCommand(Message message) {
        DataResponse dataResponse;
        String response;
        if (checkUser(message) != null) {
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

    private <T> T getFromESP(String url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType).getBody();
    }

    private String checkUser(Message message) {
        List<String> listOfUsers = Arrays.asList(botConfig.getUsers().split(","));
        return listOfUsers.stream().filter(element -> (element.equals(message.getChatId().toString()))).findFirst().orElse(null);
    }
}

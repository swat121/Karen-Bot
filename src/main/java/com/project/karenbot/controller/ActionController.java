package com.project.karenbot.controller;

import com.project.karenbot.dto.Client;
import com.project.karenbot.service.BotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ActionController {
    private final BotService botService;

    @PostMapping("/api/v1/bot/message")
    public String sendMessage(@RequestBody String message) {
        botService.sendMsg(message);
        return "Message was sent: '" + message + "'";
    }

    @PostMapping("/api/v1/bot/clients")
    public String addNewClient(@RequestBody Client client) {
        String message = "New client was added: " + client.toString();
        botService.sendMsg(message);
        return "Message was sent: '" + message + "'";
    }

    @PostMapping("/api/v1/bot/client/update")
    public String updateClient(@RequestBody Client client) {
        String message = "Client was updated: " + client.toString();
        botService.sendMsg(message);
        return "Message was sent: '" + message + "'";
    }

    @PostMapping("/api/v1/bot/client/connect")
    public String connectClient(@RequestBody Client client) {
        String message = "Client was connected: " + client.toString();
        botService.sendMsg(message);
        return "Message was sent: '" + message + "'";
    }
}

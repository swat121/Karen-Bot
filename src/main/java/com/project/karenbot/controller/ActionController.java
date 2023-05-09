package com.project.karenbot.controller;

import com.project.karenbot.service.BotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ActionController {
    private final BotService botService;

    @GetMapping("/api/v1/bot/message")
    public String sendMessage() {
        botService.sendMsg("Test message");
        return "Message was sent";
    }
}

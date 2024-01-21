package com.project.karenbot.controller;

import com.project.karenbot.dto.Notify;
import com.project.karenbot.service.BotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ActionController {
    private final BotService botService;

    @PostMapping("/api/v1/bot/notify")
    public String sendMessage(@RequestBody Notify message) {
        botService.sendMessage(message);
        return "Message was sent: '" + message + "'";
    }
}

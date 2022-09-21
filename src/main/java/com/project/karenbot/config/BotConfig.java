package com.project.karenbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String token;
    private String id;
    private Map<String, String> users;
}

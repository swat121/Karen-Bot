package com.project.karenbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String token;
    private String id;
}

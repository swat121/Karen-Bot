package com.project.karenbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String token;
    private String id;
    private String users;
}

package com.project.karenbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "service")
public class UrlConfig {
    private Map<Long, String> resource;
    private Map<String, String> credential;
}

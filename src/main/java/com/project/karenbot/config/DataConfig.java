package com.project.karenbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "microcontroller")
public class DataConfig {
    private Map<String, String> relayS;
    private Map<String, String> lightS;
}

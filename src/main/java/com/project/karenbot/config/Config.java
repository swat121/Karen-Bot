package com.project.karenbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public boolean statusMessage() {
        return false;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

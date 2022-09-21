package com.project.karenbot;

import com.project.karenbot.config.BotConfig;
import com.project.karenbot.config.DataConfig;
import com.project.karenbot.config.UrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties({UrlConfig.class, DataConfig.class, BotConfig.class})
public class KarenBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(KarenBotApplication.class, args);
	}

}

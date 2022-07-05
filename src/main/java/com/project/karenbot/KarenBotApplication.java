package com.project.karenbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class KarenBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(KarenBotApplication.class, args);
	}

}

package com.project.karenbot.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "feignClient", url = "localhost:8080")
public interface Karen {
    @GetMapping("/garry/temperature")
    String getTemperature();
}

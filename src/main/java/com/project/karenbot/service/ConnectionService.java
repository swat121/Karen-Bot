package com.project.karenbot.service;

import com.project.karenbot.config.UrlConfig;
import com.project.karenbot.model.DataResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ConnectionService {

    private final RestTemplate restTemplate;
    private final UrlConfig urlConfig;

    public String getResponse(String name, String url) {
        return getFromESP(urlConfig.getResource().get(name) + url, String.class);
    }

    public DataResponse getDataResponse(String name, String url) {
        return getFromESP(urlConfig.getResource().get(name) + url, DataResponse.class);
    }

    private <T> T getFromESP(String url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType).getBody();
    }
}

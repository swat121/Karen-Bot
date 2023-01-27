package com.project.karenbot.service;

import com.project.karenbot.model.DataResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
@Service
@AllArgsConstructor
public class ConnectionService {

    private final RestTemplate restTemplate;

    public String getResponse(String name, String url) {
        URI karenUrl = URI.create("https://" + name).resolve(url);
        return getFromESP(karenUrl.toString(), String.class);
    }

    public DataResponse getDataResponse(String name, String url) {
        URI karenUrl = URI.create("https://" + name).resolve(url);
        return getFromESP(karenUrl.toString(), DataResponse.class);
    }

    private <T> T getFromESP(String url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType).getBody();
    }
}

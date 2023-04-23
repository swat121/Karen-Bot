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

    public <T> T getResponseFromService(String name, String url, Class<T> responseType) {
        return restTemplate.getForEntity(URI.create("https://" + name).resolve(url).toString(), responseType).getBody();
    }

    public <T> T getObjectFromService(String name, String url, Class<T> responseType) {
        return restTemplate.getForObject(URI.create("https://" + name).resolve(url).toString(), responseType);
    }
}

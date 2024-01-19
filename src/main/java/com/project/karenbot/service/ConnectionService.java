package com.project.karenbot.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.util.annotation.Nullable;

import java.net.URI;
@Service
@AllArgsConstructor
public class ConnectionService {

    private final RestTemplate restTemplate;

    public <T> T getResponseFromService(String name, String url, Class<T> responseType) {
        return restTemplate.getForEntity(URI.create("https://" + name).resolve(url).toString(), responseType).getBody();
    }

    public void delete(String name, String url) {
        restTemplate.delete(URI.create("https://" + name).resolve(url).toString());
    }

    public <T> T getObjectFromService(String name, String url, Class<T> responseType) {
        return restTemplate.getForObject(URI.create("https://" + name).resolve(url).toString(), responseType);
    }

    public <T> void putRequestForService(String name, String url, @Nullable HttpEntity<T> request) {
        restTemplate.put(URI.create("https://" + name).resolve(url).toString(), request);
    }
}

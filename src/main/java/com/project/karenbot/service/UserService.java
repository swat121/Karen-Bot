package com.project.karenbot.service;

import com.project.karenbot.dto.ExternalUser;
import com.project.karenbot.enums.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ConnectionService connectionService;
    private static final String KAREN_DATA = Services.KAREN_DATA.getTitle();
    private static final String API_V1_USERS = "/api/v1/users";



    public List<String> getUsers() {
        return Stream.of(connectionService.getResponseFromService(KAREN_DATA, API_V1_USERS, ExternalUser[].class))
                .map(ExternalUser::getTelegramId)
                .toList();
    }
}

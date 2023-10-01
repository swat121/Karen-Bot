package com.project.karenbot.service;

import com.project.karenbot.dto.board.BoardConfig;
import com.project.karenbot.dto.board.Device;
import com.project.karenbot.enums.Services;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BoardConfigService {
    private final ConnectionService connectionService;

    private final String KAREN_DATA = Services.KAREN_DATA.getTitle();
    private final String API_V1_BOARDS = "/api/v1/boards";

    private List<BoardConfig> boardConfigList;

    public void uploadData() {
        boardConfigList = Arrays.asList(connectionService.getResponseFromService(KAREN_DATA, API_V1_BOARDS, BoardConfig[].class));
    }

    public List<String> parseBoardsNames() {
        return boardConfigList.stream().map(BoardConfig::getName).toList();
    }

    public List<Device > getSettingByName(String boardName, String setting) {
        return boardConfigList.stream()
                .filter(el -> el.getName().equalsIgnoreCase(boardName))
                .map(BoardConfig::getSetting)
                .filter(Objects::nonNull)
                .map(s -> switch (setting.toLowerCase()) {
                    case "sensors" -> s.getSensors();
                    case "trackers" -> s.getTrackers();
                    case "switchers" -> s.getSwitchers();
                    default -> null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(Collections.emptyList());
    }

}

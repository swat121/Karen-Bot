package com.project.karenbot.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Device {

    private String moduleName;
    private List<com.project.karenbot.dto.board.Data> data;

}

package com.project.karenbot.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardConfig {

    private String name;

    private Setting setting;
}

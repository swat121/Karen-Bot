package com.project.karenbot.enums;

import lombok.Getter;

@Getter
public enum Services {

    KAREN("karen"),
    KAREN_DATA("karen-data"),
    ;

    private final String title;

    Services(String title) {
        this.title = title;
    }
}

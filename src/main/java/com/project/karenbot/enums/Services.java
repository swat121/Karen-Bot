package com.project.karenbot.enums;

public enum Services {

    KAREN("karen"),
    KAREN_DATA("karen-data"),
    ;

    private final String name;

    Services(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

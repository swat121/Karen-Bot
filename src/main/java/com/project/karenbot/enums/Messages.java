package com.project.karenbot.enums;

public enum Messages {
    START("/start"),
    TIK("/tik"),
    PING("/ping"),
    ID("/id"),
    EXCEL("/excel"),
    DELETE("/delete"),
    CLIENTS("/clients"),
    ;

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}

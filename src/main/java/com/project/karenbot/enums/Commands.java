package com.project.karenbot.enums;

public enum Commands {

    BACK_LIGHT("Back Light"),
    MAIN_LIGHT("Main Light"),
    OPEN("Open"),
    STATUS("Status"),
    ;

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}

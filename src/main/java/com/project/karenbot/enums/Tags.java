package com.project.karenbot.enums;

import lombok.Getter;

@Getter
public enum Tags {
    SETTING_TAG("setting"),
    MODULE_TAG("module"),

    ACTION_TAG("action");

    private final String tag;
    Tags(String tag) {
        this.tag = tag;
    }
}

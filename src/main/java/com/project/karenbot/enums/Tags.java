package com.project.karenbot.enums;

import lombok.Getter;

@Getter
public enum Tags {
    SETTING_TAG("setting"),
    MODULE_TAG("module");

    private final String tag;
    Tags(String tag) {
        this.tag = tag;
    }
}

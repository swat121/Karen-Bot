package com.project.karenbot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor(onConstructor_ = {@JsonCreator})
public class Notify {
    @NonNull
    @JsonProperty("message")
    String message;

    @NonNull
    @JsonProperty("telegramIds")
    List<String> telegramIds;
}

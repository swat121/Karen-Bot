package com.project.karenbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUser {

    private String telegramId;

    private String name;

    private Boolean isNotify;

    private String role;
}

package com.project.karenbot.model;

import lombok.Data;

import java.util.Date;

@Data
public class Temperature {
    private Long id;

    private int degreesCelsius;

    private Date date;

    private Date time;
}

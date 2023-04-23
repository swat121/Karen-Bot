package com.project.karenbot.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Temperature {

    private Long id;
    private double degreesCelsius;

    private String date;

    private String time;
}

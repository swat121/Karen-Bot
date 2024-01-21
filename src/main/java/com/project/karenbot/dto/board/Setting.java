package com.project.karenbot.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Setting {
    private List<Device> sensors;
    private List<Device> trackers;
    private List<Device> switchers;
}

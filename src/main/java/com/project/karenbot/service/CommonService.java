package com.project.karenbot.service;

import com.project.karenbot.dto.board.Data;
import com.project.karenbot.dto.board.Device;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.project.karenbot.enums.Tags.ACTION_TAG;
import static com.project.karenbot.enums.Tags.MODULE_TAG;

@Service
@AllArgsConstructor
public class CommonService {

    private final BoardConfigService boardConfigService;

    public HashMap<String, String> parseSettingCallbackData(String callbackData) {
        String boardName = callbackData.split("_")[0];
        String settingName = callbackData.split("_")[1];
        List<Device> devices = boardConfigService.getSettingByName(boardName, settingName);
        HashMap<String, String> moduleData = new HashMap<>();
        for (Device device : devices) {
            moduleData.put(device.getModuleName(), boardName + "_" +
                    settingName + "_" +
                    device.getModuleName()
                    + "_" + MODULE_TAG.getTag());

        }

        return moduleData;
    }

    public HashMap<String, String> parseModuleCallbackData(String callbackData) {
        String boardName = callbackData.split("_")[0];
        String settingName = callbackData.split("_")[1];
        String moduleName = callbackData.split("_")[2];

        List<Device> devices = boardConfigService.getSettingByName(boardName, settingName);
        Device device = devices.stream().filter((el) -> el.getModuleName().equalsIgnoreCase(moduleName)).findFirst().orElse(null);
        HashMap<String, String> moduleData = new HashMap<>();
        for (Data data : device.getData()) {
            moduleData.put(moduleName + data.getModuleId(),
                    boardName + "_" +
                            settingName + "_" +
                            moduleName + "_" +
                            data.getModuleId() + "_" +
                            ACTION_TAG.getTag());
        }

        return moduleData;
    }
}

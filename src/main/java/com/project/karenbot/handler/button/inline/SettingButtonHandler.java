package com.project.karenbot.handler.button.inline;

import com.project.karenbot.dto.board.Data;
import com.project.karenbot.dto.board.Device;
import com.project.karenbot.enums.Types;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.BoardConfigService;
import com.project.karenbot.service.ButtonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@AllArgsConstructor
public class SettingButtonHandler extends AbstractMessageHandler {

    private List<String> settingNames = Arrays.asList("sensor", "switcher");
    private final ButtonService buttonService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return  update.hasCallbackQuery()
                && settingNames.contains(update.getCallbackQuery().getData().split("_")[1])
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        String boardName = callbackData.split("_")[0];
        String settingName = callbackData.split("_")[1];
        List<Device> devices = BoardConfigService.getSettingByName(boardName, settingName);
        HashMap<String, String> moduleData = new HashMap<>();
        for (Device device: devices) {
            for (Data data: device.getData()) {
                moduleData.put(device.getModuleName(), callbackData + "_" + device.getModuleName() + "_" + data.getModuleId());
            }
        }
        return buttonService.setInlineKeyboardButton(update.getMessage(), moduleData);
    }

    @Override
    public Types getTypeOfMethod() {
        return null;
    }
}

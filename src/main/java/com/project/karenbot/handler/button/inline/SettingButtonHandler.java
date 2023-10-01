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

import static com.project.karenbot.enums.Tags.MODULE_TAG;
import static com.project.karenbot.enums.Tags.SETTING_TAG;

@Component
@AllArgsConstructor
public class SettingButtonHandler extends AbstractMessageHandler {

    private final ButtonService buttonService;
    private final BoardConfigService boardConfigService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return  update.hasCallbackQuery()
                && Arrays.stream(update.getCallbackQuery().getData().split("_")).anyMatch((el) -> el.equalsIgnoreCase(SETTING_TAG.getTag()))
                && user;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        String boardName = callbackData.split("_")[0];
        String settingName = callbackData.split("_")[1];
        List<Device> devices = boardConfigService.getSettingByName(boardName, settingName);
        HashMap<String, String> moduleData = new HashMap<>();
        for (Device device: devices) {
            for (Data data: device.getData()) {
                moduleData.put(device.getModuleName(), boardName + "_" +
                                settingName + "_" +
                                device.getModuleName() + "_" +
                                data.getModuleId() + "_" + MODULE_TAG.getTag());
            }
        }
        return buttonService.setInlineKeyboardButton(update.getMessage(), moduleData);
    }

    @Override
    public Types getTypeOfMethod() {
        return null;
    }
}

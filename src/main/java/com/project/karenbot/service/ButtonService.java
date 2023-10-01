package com.project.karenbot.service;

import com.project.karenbot.enums.Messages;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Service
public class ButtonService {

    public SendMessage setReplyKeyboardButton(Message message, List<String> buttons, Optional<String> text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();

        for (String button : buttons) {
            keyboardRowFirst.add(new KeyboardButton(button));
        }
        keyboardRowList.add(keyboardRowFirst);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        text.ifPresent(sendMessage::setText);
        return sendMessage;
    }

    public SendMessage setReplyKeyboardButton(Message message, List<String> buttons) {
        return setReplyKeyboardButton(message, buttons, Optional.empty());
    }

    public SendMessage setInlineKeyboardButton(Message message, HashMap<String, String> buttons, Optional<String> text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        List<InlineKeyboardButton> row = new ArrayList<>();
        for (Map.Entry<String, String> entry : buttons.entrySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(entry.getKey());
            button.setCallbackData(entry.getValue());
            row.add(button);
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(markup);

        text.ifPresent(sendMessage::setText);
        return sendMessage;
    }

    public SendMessage setInlineKeyboardButton(Message message, HashMap<String, String> buttons) {
        return setInlineKeyboardButton(message, buttons, Optional.empty());
    }
}

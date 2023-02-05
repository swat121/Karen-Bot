package com.project.karenbot.handler.command;

import com.project.karenbot.config.DataConfig;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.model.Temperature;
import com.project.karenbot.service.ConnectionService;
import com.project.karenbot.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class TempCommandHandler extends AbstractMessageHandler {

    private final ConnectionService connectionService;
    private final ExcelService excelService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "Temp Excel".equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendDocument handleMessage(Update update) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(update.getMessage().getChatId().toString());
        sendDocument.setDocument(excelService.createExcelFile(connectionService.getResponseFromService("karen-data", "/api/temps", Temperature[].class)));
        return sendDocument;
    }
}

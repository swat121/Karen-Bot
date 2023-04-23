package com.project.karenbot.handler.message;

import com.project.karenbot.dto.Temperature;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.service.ConnectionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileOutputStream;

@Component
@AllArgsConstructor
public class ExcelMessageHandler extends AbstractMessageHandler {
    private final ConnectionService connectionService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && "/excel".equals(update.getMessage().getText())
                && user;
    }

    @Override
    public SendDocument handleMessage(Update update) {
        File file = getExcelFile();
        SendDocument request = new SendDocument();
        request.setChatId(update.getMessage().getChatId().toString());
        request.setDocument(new InputFile(file));
        request.setCaption("This is the Excel file with Temperature");
        return request;
    }

    @SneakyThrows
    private File getExcelFile() {
        Temperature[] temperatures = connectionService.getObjectFromService("karen-data", "/api/temps", Temperature[].class);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        int rowNum = 0;
        for (Temperature temperature : temperatures) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(temperature.getDate()));
            row.createCell(1).setCellValue(String.valueOf(temperature.getTime()));
            row.createCell(2).setCellValue(temperature.getDegreesCelsius());
        }

        File tempFile = File.createTempFile("data", ".xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();

        return tempFile;
    }
}

package com.project.karenbot.handler.message;

import com.project.karenbot.dto.Sensor;
import com.project.karenbot.handler.AbstractMessageHandler;
import com.project.karenbot.enums.Types;
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

import static com.project.karenbot.enums.Messages.EXCEL;
import static com.project.karenbot.enums.Services.KAREN_DATA;

@Component
@AllArgsConstructor
public class ExcelMessageHandler extends AbstractMessageHandler {
    private final ConnectionService connectionService;

    @Override
    public boolean canHandle(Update update, boolean user) {
        return update.hasMessage()
                && EXCEL.getMessage().equals(update.getMessage().getText())
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
        Sensor[] sensors = connectionService.getObjectFromService(KAREN_DATA.getTitle(), "/api/sensors", Sensor[].class);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        int rowNum = 0;
        for (Sensor sensor : sensors) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(sensor.getDate()));
            row.createCell(1).setCellValue(String.valueOf(sensor.getTime()));
            row.createCell(2).setCellValue(sensor.getName());
            row.createCell(3).setCellValue(sensor.getData());
        }

        File tempFile = File.createTempFile("data", ".xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();

        return tempFile;
    }

    @Override
    public Types getTypeOfMethod() {
        return Types.SendDocument;
    }
}

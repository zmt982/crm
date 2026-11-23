package ru.crm.report_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductClient productClient;
    private final S3StorageService s3StorageService;

    public void generateDailyReport() {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");
            AtomicInteger rowNum = new AtomicInteger(1);

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Название");
            header.createCell(2).setCellValue("Цена");
            header.createCell(3).setCellValue("Количество");
            header.createCell(4).setCellValue("Описание");

            productClient.processProductStream(product -> {
                Row row = sheet.createRow(rowNum.getAndIncrement());
                row.createCell(0).setCellValue(product.id());
                row.createCell(1).setCellValue(product.name());
                row.createCell(2).setCellValue(product.price().doubleValue());
                row.createCell(3).setCellValue(product.quantity());
                row.createCell(4).setCellValue(product.description() != null ? product.description() : "");
            });

            workbook.write(outputStream);

            String fileName = "daily_report_" + LocalDate.now() + ".xlsx";
            s3StorageService.uploadFile(fileName, outputStream.toByteArray());

            log.info("Отчет сгенерирован и успешно загружен");
        } catch (Exception e) {
            log.error("Ошибка при генерации отчета", e);
            throw new RuntimeException(e);
        }
    }

}

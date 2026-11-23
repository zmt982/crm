package ru.crm.report_service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.crm.report_service.config.SchedulerConfig;
import ru.crm.report_service.service.ReportService;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final SchedulerConfig schedulerConfig;
    private final ReportService reportService;

    @Scheduled(
            initialDelayString = "${report-scheduler.initial-delay-seconds}",
            cron = "${report-scheduler.cron}")
    public void scheduler() throws InterruptedException {
        if (!schedulerConfig.enabled()) {
            log.warn("Scheduler disabled");
            return;
        }

        log.info("Запуск ежедневной генерации отчета по товарам...");
        reportService.generateDailyReport();
        log.info("Ежедневный отчет сгенерирован и загружен в S3");
    }

}

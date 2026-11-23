package ru.crm.report_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("report-scheduler")
public record SchedulerConfig(
        boolean enabled,
        int initialDelaySeconds,
        String cron
) {}

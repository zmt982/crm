package ru.crm.analyticservice.service;

import ru.crm.analyticservice.dto.AnalyticDto;
import ru.crm.common_lib.dto.event.SendToAnalyticsEvent;

import java.time.Instant;
import java.util.List;

public interface AnalyticService {

    List<AnalyticDto> getAnalytics(Instant start, Instant end);

    void handle(SendToAnalyticsEvent event);

}

package ru.crm.analyticservice.service;

import ru.crm.analyticservice.dto.AnalyticDto;
import ru.crm.common_lib.dto.event.SendToPartnerEvent;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticService {

    List<AnalyticDto> getAnalytics(LocalDate start, LocalDate end);

    void handle(SendToPartnerEvent event);

}

package ru.crm.analyticservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.crm.analyticservice.service.AnalyticService;
import ru.crm.common_lib.dto.event.SendToAnalyticsEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticEventListener {

    private final AnalyticService analyticService;

    @KafkaListener(topics = "${kafka.topic.analytic-events}", groupId = "analytic-service")
    public void handle(SendToAnalyticsEvent event) {
        log.info("Received event: eventId={}, productId={}", event.eventId(), event.productId());
        try {
            analyticService.handle(event);
            log.info("Analytic event processed: eventId={}", event.eventId());
        } catch (Exception e) {
            log.error("Failed to handle analytic event: eventId={}", event.eventId());
            throw e;
        }
    }

}

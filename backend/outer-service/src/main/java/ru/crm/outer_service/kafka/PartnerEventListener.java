package ru.crm.outer_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.crm.outer_service.service.OuterService;
import ru.crm.common_lib.dto.event.SendToPartnerEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class PartnerEventListener {

    private final OuterService outerService;

    @KafkaListener(topics = "${kafka.topic.partner-events}", groupId = "outer-service")
    public void handle(SendToPartnerEvent event) {
        log.info("Received event: eventId={}, productId={}", event.eventId(), event.productId());
        try {
            outerService.handle(event);
            log.info("Event processed: eventId={}", event.eventId());
        } catch (Exception e) {
            log.error("Failed to handle event: eventId={}", event.eventId(), e);
            throw e;
        }
    }

}

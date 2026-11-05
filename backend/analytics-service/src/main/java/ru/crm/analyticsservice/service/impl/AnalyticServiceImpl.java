package ru.crm.analyticsservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.analyticsservice.dto.AnalyticDto;
import ru.crm.analyticsservice.entity.AnalyticEntity;
import ru.crm.analyticsservice.mapper.AnalyticMapper;
import ru.crm.analyticsservice.repository.AnalyticRepository;
import ru.crm.analyticsservice.service.AnalyticService;
import ru.crm.common_lib.dto.event.SendToAnalyticsEvent;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticServiceImpl implements AnalyticService {

    private final AnalyticRepository repository;

    private final AnalyticMapper mapper;

    @Override
    public List<AnalyticDto> getAnalytics(Instant start, Instant end) {
        return mapper.toDto(repository.findBySentAtBetween(start, end));
    }

    @Override
    public void handle(SendToAnalyticsEvent event) {
        Optional<AnalyticEntity> existEntity = repository.findByEventId(event.eventId());

        if (existEntity.isPresent()) {
            return;
        }

        AnalyticEntity entity = AnalyticEntity.builder()
                .eventId(event.eventId())
                .productId(event.productId())
                .name(event.name())
                .quantity(event.quantity())
                .sentAt(event.sentAt())
                .build();
        repository.save(entity);
    }

}

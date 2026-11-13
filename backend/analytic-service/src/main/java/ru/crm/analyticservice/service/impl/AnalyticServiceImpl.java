package ru.crm.analyticservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.analyticservice.dto.AnalyticDto;
import ru.crm.analyticservice.entity.AnalyticEntity;
import ru.crm.analyticservice.mapper.AnalyticMapper;
import ru.crm.analyticservice.repository.AnalyticRepository;
import ru.crm.analyticservice.service.AnalyticService;
import ru.crm.common_lib.dto.event.SendToPartnerEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticServiceImpl implements AnalyticService {

    private final AnalyticRepository repository;

    private final AnalyticMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AnalyticDto> getAnalytics(LocalDate start, LocalDate end) {
        Instant startInstant = start.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = end.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant()
                .minusNanos(1);
        return mapper.toDto(repository.findBySentAtBetween(startInstant, endInstant));
    }

    @Override
    public void handle(SendToPartnerEvent event) {
        Optional<AnalyticEntity> existEntity = repository.findByEventId(event.eventId());

        if (existEntity.isPresent()) {
            return;
        }

        AnalyticEntity entity = AnalyticEntity.builder()
                .eventId(event.eventId())
                .productId(event.productId())
                .name(event.name())
                .price(event.price())
                .quantity(event.quantity())
                .description(event.description())
                .sentAt(event.sentAt())
                .build();
        repository.save(entity);
    }

}

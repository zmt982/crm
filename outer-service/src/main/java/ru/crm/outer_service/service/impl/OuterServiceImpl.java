package ru.crm.outer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.outer_service.entity.ProcessedEvent;
import ru.crm.outer_service.mapper.OuterMapper;
import ru.crm.outer_service.repository.OuterRepository;
import ru.crm.outer_service.repository.ProcessedEventRepository;
import ru.crm.outer_service.service.OuterService;
import ru.crm.product_service.dto.event.SendToPartnerEvent;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OuterServiceImpl implements OuterService {

    private final OuterRepository outerRepository;

    private final ProcessedEventRepository eventRepository;

    private final OuterMapper outerMapper;

    private static final String OUTER_ENTITY_NOT_FOUND = "OuterEntity not found: ";

    @Override
    public OuterResponseDto create(OuterRequestDto outerRequestDto) {
        OuterEntity entity = outerMapper.toEntity(outerRequestDto);
        OuterEntity created = outerRepository.save(entity);
        return outerMapper.toDto(created);
    }

    @Override
    public OuterResponseDto update(Long id, OuterRequestDto updateRequestDto) {
        OuterEntity updateEntity = outerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OUTER_ENTITY_NOT_FOUND + id));

        updateEntity.setName(updateRequestDto.name());
        updateEntity.setPrice(updateRequestDto.price());
        updateEntity.setQuantity(updateRequestDto.quantity());
        updateEntity.setDescription(updateRequestDto.description());

        return outerMapper.toDto(updateEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public OuterResponseDto getById(Long id) {
        OuterEntity foundEntity = outerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OUTER_ENTITY_NOT_FOUND + id));
        return outerMapper.toDto(foundEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!outerRepository.existsById(id)) {
            throw new EntityNotFoundException(OUTER_ENTITY_NOT_FOUND + id);
        }
        outerRepository.deleteById(id);
    }

    @Override
    public OuterResponseDto handle(SendToPartnerEvent sentEvent) {
        Optional<ProcessedEvent> existEvent = eventRepository.findByEventId(sentEvent.eventId());

        if (existEvent.isPresent()) {
            return null;
        }

        OuterEntity outerEntity = outerRepository.findById(sentEvent.productId())
                .orElseGet(() -> outerMapper.toEntity(sentEvent));

        Integer receivedQuantity = sentEvent.quantity();
        Integer existQuantity = outerEntity.getQuantity();
        outerEntity.setQuantity(existQuantity + receivedQuantity);
        OuterEntity saved = outerRepository.save(outerEntity);

        ProcessedEvent processedEvent = ProcessedEvent.builder()
                .eventId(sentEvent.eventId())
                .processedAt(Instant.now())
                .build();
        eventRepository.save(processedEvent);

        return outerMapper.toDto(saved);
    }

}

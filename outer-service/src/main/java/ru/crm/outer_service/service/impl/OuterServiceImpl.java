package ru.crm.outer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.outer_service.mapper.OuterMapper;
import ru.crm.outer_service.repository.OuterRepository;
import ru.crm.outer_service.service.OuterService;

@Service
@RequiredArgsConstructor
public class OuterServiceImpl implements OuterService {
    private final OuterRepository outerRepository;
    private final OuterMapper outerMapper;
    private final String OUTER_ENTITY_NOT_FOUND = "OuterEntity not found: ";

    @Override
    @Transactional
    public OuterResponseDto create(OuterRequestDto outerRequestDto) {
        OuterEntity entity = outerMapper.toEntity(outerRequestDto);
        OuterEntity created = outerRepository.save(entity);
        return outerMapper.toDto(created);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteById(Long id) {
        if (!outerRepository.existsById(id)) {
            throw new EntityNotFoundException(OUTER_ENTITY_NOT_FOUND + id);
        }
        outerRepository.deleteById(id);
    }

}

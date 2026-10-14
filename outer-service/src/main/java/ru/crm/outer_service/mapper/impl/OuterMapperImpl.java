package ru.crm.outer_service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.outer_service.mapper.OuterMapper;

@Component
public class OuterMapperImpl implements OuterMapper {
    @Override
    public OuterResponseDto toDto(OuterEntity outerEntity) {
        if (outerEntity == null) {
            return null;
        }

        return new OuterResponseDto(
                outerEntity.getId(),
                outerEntity.getName(),
                outerEntity.getPrice(),
                outerEntity.getQuantity(),
                outerEntity.getDescription());
    }

    @Override
    public OuterEntity toEntity(OuterRequestDto outerRequestDto) {
        if (outerRequestDto == null) {
            return null;
        }

        return OuterEntity.builder()
                .name(outerRequestDto.name())
                .price(outerRequestDto.price())
                .quantity(outerRequestDto.quantity())
                .description(outerRequestDto.description())
                .build();
    }

}

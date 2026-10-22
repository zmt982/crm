package ru.crm.outer_service.mapper;

import org.springframework.stereotype.Component;
import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.common_lib.dto.event.SendToPartnerEvent;

@Component
public class OuterMapper {

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

    public OuterEntity toEntity(SendToPartnerEvent sentEvent) {
        if (sentEvent == null) {
            return null;
        }

        return OuterEntity.builder()
                .id(sentEvent.productId())
                .name(sentEvent.name())
                .price(sentEvent.price())
                .quantity(sentEvent.quantity())
                .description(sentEvent.description())
                .build();
    }

}

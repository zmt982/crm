package ru.crm.outer_service.mapper;

import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.product_service.dto.event.SendToPartnerEvent;

public interface OuterMapper {

    OuterResponseDto toDto(OuterEntity outerEntity);

    OuterEntity toEntity(OuterRequestDto outerRequestDto);

    OuterEntity toEntity(SendToPartnerEvent sentEvent);

}

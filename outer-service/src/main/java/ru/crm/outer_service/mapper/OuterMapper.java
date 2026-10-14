package ru.crm.outer_service.mapper;

import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;

public interface OuterMapper {

    OuterResponseDto toDto(OuterEntity outerEntity);

    OuterEntity toEntity(OuterRequestDto outerRequestDto);
}

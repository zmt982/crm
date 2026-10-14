package ru.crm.outer_service.mapper.impl;

import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.entity.OuterEntity;
import ru.crm.outer_service.mapper.OuterMapper;

public class OuterMapperImpl implements OuterMapper {
    @Override
    public OuterResponseDto toDto(OuterEntity outerEntity) {
        if(outerEntity == null) {
            return null;
        }


    }

    @Override
    public OuterEntity toEntity(OuterRequestDto outerRequestDto) {
        return null;
    }
}

package ru.crm.outer_service.service;

import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.common_lib.dto.event.SendToPartnerEvent;

public interface OuterService {

    OuterResponseDto create(OuterRequestDto outerRequestDto);

    OuterResponseDto update(Long id, OuterRequestDto outerRequestDto);

    OuterResponseDto getById(Long id);

    void deleteById(Long id);

    void handle(SendToPartnerEvent event);

}

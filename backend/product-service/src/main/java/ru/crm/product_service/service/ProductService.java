package ru.crm.product_service.service;

import ru.crm.product_service.dto.ProductDto;
import ru.crm.product_service.dto.SendToPartnerRequestDto;
import ru.crm.product_service.dto.SendToPartnerResponseDto;

public interface ProductService {

    ProductDto create(ProductDto createDto);

    ProductDto update(Long id, ProductDto updateDto);

    ProductDto getById(Long id);

    void deleteById(Long id);

    SendToPartnerResponseDto sendToPartner(Long id, SendToPartnerRequestDto requestDto);

}

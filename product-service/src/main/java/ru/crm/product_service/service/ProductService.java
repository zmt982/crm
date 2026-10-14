package ru.crm.product_service.service;

import ru.crm.product_service.dto.ProductDto;

public interface ProductService {

    ProductDto create(ProductDto createDto);

    ProductDto update(Long id, ProductDto updateDto);

    ProductDto getById(Long id);

    void deleteById(Long id);
}

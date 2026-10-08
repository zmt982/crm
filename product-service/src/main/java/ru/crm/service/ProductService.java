package ru.crm.service;

import ru.crm.dto.ProductDto;

public interface ProductService {

    ProductDto create(ProductDto createDto);

    ProductDto update(Long id, ProductDto updateDto);

    ProductDto getById(Long id);

    void deleteById(Long id);
}

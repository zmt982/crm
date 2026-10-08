package ru.crm.mapper;

import ru.crm.dto.ProductDto;
import ru.crm.entity.ProductEntity;

public interface ProductMapper {

    ProductDto toDto(ProductEntity productEntity);

    ProductEntity toEntity(ProductDto productDto);
}

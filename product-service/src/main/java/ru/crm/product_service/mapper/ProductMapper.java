package ru.crm.product_service.mapper;

import ru.crm.product_service.dto.ProductDto;
import ru.crm.product_service.entity.ProductEntity;

public interface ProductMapper {

    ProductDto toDto(ProductEntity productEntity);

    ProductEntity toEntity(ProductDto productDto);
}

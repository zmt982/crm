package ru.crm.mapper.impl;

import ru.crm.dto.ProductDto;
import ru.crm.entity.ProductEntity;
import ru.crm.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto toDto(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        return new ProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getQuantity(),
                productEntity.getDescription());
    }

    @Override
    public ProductEntity toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return new ProductEntity(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                productDto.quantity(),
                productDto.description()
        );
    }
}

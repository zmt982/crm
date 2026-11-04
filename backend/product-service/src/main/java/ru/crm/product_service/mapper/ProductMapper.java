package ru.crm.product_service.mapper;

import org.springframework.stereotype.Component;
import ru.crm.product_service.dto.ProductDto;
import ru.crm.product_service.entity.ProductEntity;

@Component
public class ProductMapper {

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

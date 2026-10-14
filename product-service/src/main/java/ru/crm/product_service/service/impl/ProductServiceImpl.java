package ru.crm.product_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.product_service.dto.ProductDto;
import ru.crm.product_service.entity.ProductEntity;
import ru.crm.product_service.mapper.ProductMapper;
import ru.crm.product_service.repository.ProductRepository;
import ru.crm.product_service.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final String PRODUCT_ENTITY_NOT_FOUND = "Product not found: ";

    @Override
    @Transactional
    public ProductDto create(ProductDto createDto) {
        ProductEntity entity = productMapper.toEntity(createDto);
        ProductEntity created = productRepository.save(entity);
        return productMapper.toDto(created);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto updateDto) {
        ProductEntity updateEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_ENTITY_NOT_FOUND + id));

        updateEntity.setName(updateDto.name());
        updateEntity.setPrice(updateDto.price());
        updateEntity.setQuantity(updateDto.quantity());
        updateEntity.setDescription(updateDto.description());

        return productMapper.toDto(updateEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        ProductEntity foundEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_ENTITY_NOT_FOUND + id));
        return productMapper.toDto(foundEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_ENTITY_NOT_FOUND + id);
        }
        productRepository.deleteById(id);
    }
}

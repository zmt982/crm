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
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));

        updateEntity.setName(updateDto.name());
        updateEntity.setPrice(updateDto.price());
        updateEntity.setQuantity(updateDto.quantity());

        if (updateDto.description() != null) {
            updateEntity.setDescription(updateDto.description());
        }

        return productMapper.toDto(updateEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        ProductEntity foundEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        return productMapper.toDto(foundEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}

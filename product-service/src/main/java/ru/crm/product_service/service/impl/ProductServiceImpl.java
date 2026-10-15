package ru.crm.product_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.product_service.dto.ProductDto;
import ru.crm.product_service.dto.SendToPartnerRequestDto;
import ru.crm.product_service.dto.SendToPartnerResponseDto;
import ru.crm.product_service.dto.Status;
import ru.crm.product_service.dto.event.SendToPartnerEvent;
import ru.crm.product_service.entity.ProductEntity;
import ru.crm.product_service.exception.InsufficientQuantityException;
import ru.crm.product_service.exception.PartnerUnavailableException;
import ru.crm.product_service.mapper.ProductMapper;
import ru.crm.product_service.repository.ProductRepository;
import ru.crm.product_service.service.ProductService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final KafkaTemplate<String, SendToPartnerEvent> kafkaTemplate;
    @Value("${kafka.topic.partner-events}")
    private String partnerTopic;
    private static final String PRODUCT_NOT_FOUND = "Product not found: ";

    @Override
    public ProductDto create(ProductDto createDto) {
        ProductEntity entity = productMapper.toEntity(createDto);
        ProductEntity created = productRepository.save(entity);
        return productMapper.toDto(created);
    }

    @Override
    public ProductDto update(Long id, ProductDto updateDto) {
        ProductEntity updateEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND + id));

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
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND + id));
        return productMapper.toDto(foundEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public SendToPartnerResponseDto sendToPartner(Long id, SendToPartnerRequestDto requestDto) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND + id));

        if (requestDto.quantity() > productEntity.getQuantity()) {
            throw new InsufficientQuantityException(String.format("Not enough quantity. Requested: %d, available: %d",
                    requestDto.quantity(), productEntity.getQuantity()));
        }

        Integer remainingQuantity = productEntity.getQuantity() - requestDto.quantity();
        productEntity.setQuantity(remainingQuantity);
        UUID eventId = UUID.randomUUID();
        Instant sentAt = Instant.now();
        SendToPartnerEvent event = SendToPartnerEvent.builder()
                .eventId(eventId)
                .productId(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .quantity(requestDto.quantity())
                .description(productEntity.getDescription())
                .sentAt(sentAt)
                .build();

        try {
            kafkaTemplate.send(partnerTopic, productEntity.getId().toString(), event)
                    .get();
        } catch (Exception e) {
            throw new PartnerUnavailableException("Kafka unavailable", e);
        }

        return SendToPartnerResponseDto.builder()
                .id(productEntity.getId())
                .sentQuantity(requestDto.quantity())
                .remainingQuantity(remainingQuantity)
                .status(Status.SENT)
                .eventId(eventId)
                .sentAt(sentAt)
                .build();
    }

}

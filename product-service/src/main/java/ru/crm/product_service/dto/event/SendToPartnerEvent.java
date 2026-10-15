package ru.crm.product_service.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public class SendToPartnerEvent {

    @NotBlank
    UUID eventId;

    @NotBlank
    Long productId;

    @NotBlank
    String name;

    @NotNull
    @Positive
    BigDecimal price;

    @NotBlank
    @Positive
    Integer quantity;

    String description;

    Instant sentAt;
}

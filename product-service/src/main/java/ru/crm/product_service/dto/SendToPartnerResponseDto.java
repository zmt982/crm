package ru.crm.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SendToPartnerResponseDto(

        @NotBlank
        Long id,

        @NotBlank
        @Positive
        Integer sentQuantity,

        @NotBlank
        @PositiveOrZero
        Integer remainingQuantity,

        Status status,

        UUID eventId,

        Instant sentAt

) {
}

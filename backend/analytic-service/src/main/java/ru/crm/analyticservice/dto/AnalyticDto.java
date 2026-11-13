package ru.crm.analyticservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AnalyticDto(

        Long id,

        @NotNull
        UUID eventId,

        @NotNull
        Long productId,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        String description,

        @NotNull
        Instant sentAt

) {
}

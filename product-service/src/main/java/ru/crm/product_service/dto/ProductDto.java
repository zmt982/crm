package ru.crm.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDto(
        Long id,

        @NotBlank
        @Size(max = 50)
        String name,

        @NotNull @Positive
        BigDecimal price,

        @NotNull @PositiveOrZero
        Integer quantity,

        String description
) {}

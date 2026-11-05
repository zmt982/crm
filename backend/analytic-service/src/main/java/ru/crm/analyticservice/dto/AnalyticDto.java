package ru.crm.analyticservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AnalyticDto(

        Long id,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        @NotNull
        Instant sentAt

) {
}

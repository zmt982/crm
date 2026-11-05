package ru.crm.common_lib.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record SendToAnalyticsEvent(

        @NotNull
        UUID eventId,

        @NotNull
        Long productId,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        @NotNull
        Instant sentAt

) {}

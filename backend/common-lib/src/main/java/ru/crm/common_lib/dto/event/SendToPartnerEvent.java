package ru.crm.common_lib.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SendToPartnerEvent(

        @NotNull
        UUID eventId,

        @NotNull
        Long productId,

        @NotBlank
        @Size(max = 50)
        String name,

        @NotNull
        @Positive BigDecimal price,

        @NotNull
        @Positive
        Integer quantity,

        String description,

        @NotNull
        Instant sentAt

) {}

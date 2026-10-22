package ru.crm.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SendToPartnerRequestDto (

        @NotNull
        @Positive
        Integer quantity

){}

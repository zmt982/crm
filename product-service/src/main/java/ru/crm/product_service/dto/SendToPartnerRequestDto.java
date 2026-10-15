package ru.crm.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SendToPartnerRequestDto (

        @NotBlank
        Long id,

        @NotBlank
        @Positive
        Integer quantity

){}

package ru.crm.report_service.report;

import java.math.BigDecimal;

public record ProductRow(
        Long id,
        String name,
        BigDecimal price,
        Integer quantity,
        String description
) {}

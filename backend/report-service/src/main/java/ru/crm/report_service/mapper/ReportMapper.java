package ru.crm.report_service.mapper;

import org.springframework.stereotype.Component;
import ru.crm.report_service.report.ProductRow;
import ru.crm.common_lib.dto.ProductDto;

@Component
public class ReportMapper {

    public ProductRow toRow(ProductDto productDto) {
        return new ProductRow(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                productDto.quantity(),
                productDto.description()
        );
    }

}

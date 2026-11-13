package ru.crm.analyticservice.mapper;

import org.springframework.stereotype.Component;
import ru.crm.analyticservice.dto.AnalyticDto;
import ru.crm.analyticservice.entity.AnalyticEntity;

import java.util.List;
import java.util.Objects;

@Component
public class AnalyticMapper {

    public AnalyticDto toDto(AnalyticEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AnalyticDto(
                entity.getId(),
                entity.getEventId(),
                entity.getProductId(),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getDescription(),
                entity.getSentAt());
    }

    public AnalyticEntity toEntity(AnalyticDto dto) {
        if (dto == null) {
            return null;
        }

        return new AnalyticEntity(
                dto.id(),
                dto.eventId(),
                dto.productId(),
                dto.name(),
                dto.price(),
                dto.quantity(),
                dto.description(),
                dto.sentAt()
        );
    }

    public List<AnalyticDto> toDto(List<AnalyticEntity> entities) {
        return entities.stream().map(this::toDto).filter(Objects::nonNull).toList();
    }

    public List<AnalyticEntity> toEntity(List<AnalyticDto> dtos) {
        return dtos.stream().map(this::toEntity).filter(Objects::nonNull).toList();
    }

}

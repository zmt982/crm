package ru.crm.analyticsservice.mapper;

import org.springframework.stereotype.Component;
import ru.crm.analyticsservice.dto.AnalyticDto;
import ru.crm.analyticsservice.entity.AnalyticEntity;

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
                entity.getName(),
                entity.getQuantity(),
                entity.getSentAt());
    }

    public AnalyticEntity toEntity(AnalyticDto dto) {
        if (dto == null) {
            return null;
        }

        return new AnalyticEntity(
                dto.id(),
                dto.name(),
                dto.quantity(),
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

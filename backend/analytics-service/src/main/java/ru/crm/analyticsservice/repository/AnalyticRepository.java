package ru.crm.analyticsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crm.analyticsservice.entity.AnalyticEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalyticRepository extends JpaRepository<AnalyticEntity, Long> {

    List<AnalyticEntity> findBySentAtBetween(Instant start, Instant end);

    Optional<AnalyticEntity> findByEventId(UUID eventId);

}

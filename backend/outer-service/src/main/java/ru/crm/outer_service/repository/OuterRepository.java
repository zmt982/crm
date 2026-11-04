package ru.crm.outer_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crm.outer_service.entity.OuterEntity;

@Repository
public interface OuterRepository extends JpaRepository<OuterEntity, Long> {
}

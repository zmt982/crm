package ru.crm.analyticservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "analytics")
public class AnalyticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    UUID eventId;

    @Column
    @NotNull
    Long productId;

    @Column
    @NotBlank
    @Size(max = 100)
    private String name;

    @Column(precision = 10, scale = 2)
    @NotNull
    @Positive
    BigDecimal price;

    @Column
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @Column
    String description;

    @NotNull
    Instant sentAt;

}

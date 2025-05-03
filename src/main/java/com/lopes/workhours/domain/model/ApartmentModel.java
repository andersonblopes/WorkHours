package com.lopes.workhours.domain.model;

import com.lopes.workhours.domain.enums.DurationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApartmentModel {

    private Long id;
    private String description;
    private String accessCode;
    private DurationType durationType;
    private BigDecimal currencyValue;
    private BuildModel build;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private UUID uuid;
    private Integer version;
    private String descriptionFormated;
}

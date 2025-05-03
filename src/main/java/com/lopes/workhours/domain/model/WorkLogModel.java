package com.lopes.workhours.domain.model;

import com.lopes.workhours.domain.enums.DurationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class WorkLogModel {

    private Long id;
    private LocalDateTime executionDate;
    private DurationType durationType;
    private Long duration;
    private BigDecimal currencyUnitValue;
    private String notes;
    private EmployeeModel employee;
    private ApartmentModel apartment;
    private UserModel user;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private UUID uuid;
    private Integer version;
    private BigDecimal total;
}

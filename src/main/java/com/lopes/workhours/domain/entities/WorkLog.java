package com.lopes.workhours.domain.entities;

import com.lopes.workhours.domain.enums.DurationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "work_log", schema = "public")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "execution_date")
    private LocalDateTime executionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration_type")
    private DurationType durationType;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "currency_value")
    private BigDecimal currencyValue;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "uuid")
    private UUID uuid;

    @Version
    @Column(name = "version")
    private Integer version;

    @Transient
    private BigDecimal total;

    @PrePersist
    public void setDefaultValues() {
        this.setUuid(UUID.randomUUID());
        this.setActive(Boolean.TRUE);
    }

    public BigDecimal getTotal() {
        var total = BigDecimal.ZERO;
        if (Objects.nonNull(duration) && Objects.nonNull(currencyValue)) {
            total = currencyValue.multiply(BigDecimal.valueOf(duration));
        }
        return total;
    }

}

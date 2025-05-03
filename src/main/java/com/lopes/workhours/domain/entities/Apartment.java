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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "apartment", schema = "public")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "duration_type")
    @Enumerated(EnumType.STRING)
    private DurationType durationType;

    @Column(name = "currency_value")
    private BigDecimal currencyValue;

    @ManyToOne
    @JoinColumn(name = "build_id")
    private Build build;

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
    private String descriptionFormated;

    @PrePersist
    public void setDefaultValues() {
        this.setUuid(UUID.randomUUID());
        this.setActive(Boolean.TRUE);
    }

    public String getDescriptionFormated() {
        this.descriptionFormated = build.getDescription().concat(" - ").concat(description);
        return this.descriptionFormated;
    }
}

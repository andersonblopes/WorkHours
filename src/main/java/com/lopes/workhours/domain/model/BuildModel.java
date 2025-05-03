package com.lopes.workhours.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BuildModel {

    private Long id;
    private String description;
    private String address;
    private String accessCode;
    private OwnerModel owner;
    private List<ApartmentModel> apartments;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private UUID uuid;
    private Integer version;
}

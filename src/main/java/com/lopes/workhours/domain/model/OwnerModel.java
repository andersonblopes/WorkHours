package com.lopes.workhours.domain.model;

import com.lopes.workhours.domain.entities.User;
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
public class OwnerModel {

    private Long id;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private UUID uuid;
    private Integer version;
    private User user;
    private List<BuildModel> builds;
}

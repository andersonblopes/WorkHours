package com.lopes.workhours.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmployeeModel {

    private Long id;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    private UserModel user;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private UUID uuid;
    private Integer version;

}

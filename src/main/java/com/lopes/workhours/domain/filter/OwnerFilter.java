package com.lopes.workhours.domain.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerFilter {
    private String user;
    private String name;
    private String email;
    private String phone;
    private Boolean active;
}

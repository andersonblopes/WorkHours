package com.lopes.workhours.domain.filter;

import com.lopes.workhours.domain.enums.DurationType;
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
public class ApartmentFilter {
    private String build;
    private String description;
    private DurationType durationType;
    private Boolean active;
}

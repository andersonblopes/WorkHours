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
    private String userNickName;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private Boolean active;
}

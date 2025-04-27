package com.lopes.workhours.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserModel {

    private String name;
    private String nickName;
    private String email;
    private String login;
    private String password;
    private String confirmPassword;
    private Boolean active;
}

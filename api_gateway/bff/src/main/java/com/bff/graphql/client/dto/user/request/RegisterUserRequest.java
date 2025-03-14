package com.bff.graphql.client.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;
    private String phone;
}


package com.apigateway.aggregation.client.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
}


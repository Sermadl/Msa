package com.apigateway.aggregation.client.dto.user.response;

import com.apigateway.aggregation.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserRole role;
}

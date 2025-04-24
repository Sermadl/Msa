package com.userserver.user.controller.dto.response;

import com.userserver.user.model.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserRole role;
    private String username;
}

package com.userserver.user.controller.dto.response;

import com.userserver.user.model.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidTokenResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
}

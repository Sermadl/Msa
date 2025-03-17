package com.userserver.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
}

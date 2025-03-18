package com.apigateway.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private UserRole userRole;
    private String password;
    private String email;
    private String phone;

//    public User(String username, String password, String email, String phone) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.phone = phone;
//    }
}
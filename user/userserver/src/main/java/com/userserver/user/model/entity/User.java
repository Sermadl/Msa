package com.userserver.user.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String username;
    private String password;
    private String email;
    private String phone;

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.userRole = UserRole.USER;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}

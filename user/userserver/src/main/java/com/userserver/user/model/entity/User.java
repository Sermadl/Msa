package com.userserver.user.model.entity;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
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

    public void update(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}

package com.orderservice.global.util;


import com.orderservice.global.util.error.AdminOnlyException;
import com.orderservice.global.util.error.HasNoAuthorityException;

public class RoleCheck {
    public static void isUser(UserRole role) {
        if (!role.isUser()) {
            throw new HasNoAuthorityException();
        }
    }

    public static void isSeller(UserRole role) {
        if (!role.isSeller()) {
            throw new HasNoAuthorityException();
        }
    }

    public static void isAdmin(UserRole role) {
        if (!role.isAdmin()) {
            throw new AdminOnlyException();
        }
    }
}


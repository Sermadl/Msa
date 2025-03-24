package com.itemservice.global.util;


import com.itemservice.global.util.error.AdminOnlyException;
import com.itemservice.global.util.error.HasNoAuthorityException;

public class RoleCheck {
    public static void isUser(UserRole role) {
        if (!role.includes(UserRole.USER)) {
            throw new HasNoAuthorityException();
        }
    }

    public static void isSeller(UserRole role) {
        if (!role.includes(UserRole.SELLER)) {
            throw new HasNoAuthorityException();
        }
    }

    public static void isAdmin(UserRole role) {
        if (!role.isAdmin()) {
            throw new AdminOnlyException();
        }
    }
}


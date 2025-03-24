package com.orderservice.global.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum UserRole {
    GUEST("GUEST"),
    USER("USER"),
    SELLER("SELLER"),
    ADMIN(combine("ADMIN", "USER", "SELLER"));

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    // 특정 역할을 포함하는지 확인하는 메서드
    public boolean includes(UserRole required) {
        if (this == ADMIN) return true; // ADMIN은 모든 권한을 포함
        return this == required;
    }

    public static String combine(String... names) {
        return String.join(",", names);
    }

    private static final Map<String, UserRole> BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(UserRole::getName, e -> e));

    public static UserRole of(String name) {
        return BY_LABEL.get(name);
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isSeller() {
        return this == SELLER;
    }

    public boolean isUser() {
        return this == USER;
    }
}

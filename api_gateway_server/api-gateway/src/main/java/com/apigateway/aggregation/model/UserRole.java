package com.apigateway.aggregation.model;

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
    ADMIN("ADMIN");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}

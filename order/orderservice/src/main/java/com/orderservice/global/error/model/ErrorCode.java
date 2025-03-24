package com.orderservice.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED"),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "INCORRECT_PASSWORD"),
    HAS_NO_AUTHORITY(HttpStatus.UNAUTHORIZED, "HAS_NO_AUTHORITY"),
    ADMIN_ONLY(HttpStatus.FORBIDDEN, "ADMIN_ONLY"),;

    private final HttpStatus status;
    private final String message;
}

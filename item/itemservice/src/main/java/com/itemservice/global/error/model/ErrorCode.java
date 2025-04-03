package com.itemservice.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN"),
    HAS_NO_AUTHORITY(HttpStatus.FORBIDDEN, "HAS_NO_AUTHORITY"),
    ADMIN_ONLY(HttpStatus.FORBIDDEN, "ADMIN_ONLY"),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_NOT_FOUND" ),
    CATEGORY_REGISTER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CATEGORY_REGISTER_FAILED"),
    CATEGORY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "CATEGORY_NOT_FOUND" ),;

    private final HttpStatus status;
    private final String message;
}

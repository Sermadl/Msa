package com.apigateway.aggregation.controller.error;

import com.apigateway.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
    }
}

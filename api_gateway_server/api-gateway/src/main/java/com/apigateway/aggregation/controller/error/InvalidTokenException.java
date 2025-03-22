package com.apigateway.aggregation.controller.error;

import com.apigateway.global.error.model.CustomException;
import com.apigateway.global.error.model.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}

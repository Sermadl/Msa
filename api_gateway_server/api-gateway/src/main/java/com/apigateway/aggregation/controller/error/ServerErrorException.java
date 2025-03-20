package com.apigateway.aggregation.controller.error;

import com.apigateway.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class ServerErrorException extends CustomException {
    public ServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    }
}

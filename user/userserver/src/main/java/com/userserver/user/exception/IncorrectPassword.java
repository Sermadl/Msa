package com.userserver.user.exception;

import com.userserver.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class IncorrectPassword extends CustomException {
    public IncorrectPassword() {
        super(HttpStatus.BAD_REQUEST, "INCORRECT_PASSWORD");
    }
}

package com.userserver.user.exception;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class IncorrectPassword extends CustomException {
    public IncorrectPassword() {
        super(ErrorCode.INCORRECT_PASSWORD);
    }
}

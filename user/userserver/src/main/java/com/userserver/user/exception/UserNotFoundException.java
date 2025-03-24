package com.userserver.user.exception;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}

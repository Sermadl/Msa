package com.userserver.user.exception;

import com.userserver.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "USER_NOT_FOUND");
    }
}

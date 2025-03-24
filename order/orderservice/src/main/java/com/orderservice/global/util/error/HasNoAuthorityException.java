package com.orderservice.global.util.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class HasNoAuthorityException extends CustomException {
    public HasNoAuthorityException() {
        super(ErrorCode.HAS_NO_AUTHORITY);
    }
}

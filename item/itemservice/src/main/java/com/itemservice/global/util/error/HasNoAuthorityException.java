package com.itemservice.global.util.error;

import com.itemservice.global.error.model.CustomException;
import com.itemservice.global.error.model.ErrorCode;

public class HasNoAuthorityException extends CustomException {
    public HasNoAuthorityException() {
        super(ErrorCode.HAS_NO_AUTHORITY);
    }
}

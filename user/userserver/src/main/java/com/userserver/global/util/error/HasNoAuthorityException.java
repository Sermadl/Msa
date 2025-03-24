package com.userserver.global.util.error;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.ErrorCode;

public class HasNoAuthorityException extends CustomException {
    public HasNoAuthorityException() {
        super(ErrorCode.HAS_NO_AUTHORITY);
    }
}

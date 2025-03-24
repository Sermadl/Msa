package com.userserver.global.util.error;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.ErrorCode;

public class AdminOnlyException extends CustomException {
    public AdminOnlyException() {
        super(ErrorCode.ADMIN_ONLY);
    }
}

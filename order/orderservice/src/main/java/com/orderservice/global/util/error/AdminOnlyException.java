package com.orderservice.global.util.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class AdminOnlyException extends CustomException {
    public AdminOnlyException() {
        super(ErrorCode.ADMIN_ONLY);
    }
}

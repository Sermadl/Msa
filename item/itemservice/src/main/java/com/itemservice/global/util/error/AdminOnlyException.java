package com.itemservice.global.util.error;

import com.itemservice.global.error.model.CustomException;
import com.itemservice.global.error.model.ErrorCode;

public class AdminOnlyException extends CustomException {
    public AdminOnlyException() {
        super(ErrorCode.ADMIN_ONLY);
    }
}

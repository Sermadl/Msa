package com.itemservice.service.error;

import com.itemservice.global.error.model.CustomException;
import com.itemservice.global.error.model.ErrorCode;

public class CategoryRegisterException extends CustomException {
    public CategoryRegisterException() {
        super(ErrorCode.CATEGORY_REGISTER_FAILED);
    }
}

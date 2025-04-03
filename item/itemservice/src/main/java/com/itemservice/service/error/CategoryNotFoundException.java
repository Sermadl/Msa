package com.itemservice.service.error;

import com.itemservice.global.error.model.CustomException;
import com.itemservice.global.error.model.ErrorCode;

public class CategoryNotFoundException extends CustomException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}

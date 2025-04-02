package com.itemservice.service.error;

import com.itemservice.global.error.model.CustomException;
import com.itemservice.global.error.model.ErrorCode;

public class ItemNotFoundException extends CustomException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}

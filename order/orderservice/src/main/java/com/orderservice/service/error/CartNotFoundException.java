package com.orderservice.service.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class CartNotFoundException extends CustomException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND);
    }
}

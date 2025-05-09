package com.orderservice.service.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class CartItemNotFound extends CustomException {
    public CartItemNotFound() {
        super(ErrorCode.CART_ITEM_NOT_FOUND);
    }
}

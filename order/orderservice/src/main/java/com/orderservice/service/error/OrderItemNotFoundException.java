package com.orderservice.service.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class OrderItemNotFoundException extends CustomException {
    public OrderItemNotFoundException() {
        super(ErrorCode.ORDER_ITEM_NOT_FOUND);
    }
}

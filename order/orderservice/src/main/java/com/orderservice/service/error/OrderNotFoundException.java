package com.orderservice.service.error;

import com.orderservice.global.error.model.CustomException;
import com.orderservice.global.error.model.ErrorCode;

public class OrderNotFoundException extends CustomException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}

package com.orderservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponse {
    private long itemId;
    private int quantity;
    private boolean isSelected;
}
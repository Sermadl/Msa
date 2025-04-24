package com.orderservice.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartUpdateRequest {
    private int quantity;
    private boolean isSelected;
}

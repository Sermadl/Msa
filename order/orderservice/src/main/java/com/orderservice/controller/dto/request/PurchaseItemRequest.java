package com.orderservice.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PurchaseItemRequest {
    private Long itemId;
    private String name;
    private BigDecimal price;
    private int quantity;
}

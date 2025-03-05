package com.orderservice.controller.dto;

import lombok.Data;

@Data
public class PurchaseRequest {
    private Long customerId;
    private Long itemId;
    private int quantity;
    private String address;
    private String description;
}

package com.userserver.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserOrderResponse {
    private Long orderId;
    private String orderDate;
    private int quantity;
    private String address;
    private String description;
}

package com.apigateway.aggregation.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserOrderItemResponse {
    private String id;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> itemList;
    private String address;
    private String description;
    private BigDecimal totalPrice;
}

package com.apigateway.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem{
    private Long id;
    private String orderId;
    private Long itemId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private Status status;
    private LocalDateTime arrivalTime;
}

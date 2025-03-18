package com.orderservice.controller.dto.response;

import com.orderservice.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String orderId;
    private Long itemId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private Status status;
    private LocalDateTime arrivalTime;
}

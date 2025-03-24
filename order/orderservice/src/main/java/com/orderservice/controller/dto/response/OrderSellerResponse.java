package com.orderservice.controller.dto.response;

import com.orderservice.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderSellerResponse {
    private String id;
    private LocalDateTime orderDate;
    private Long customerId;
    private String address;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Status status;
    private LocalDateTime arrivalTime;
}

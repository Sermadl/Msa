package com.apigateway.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String id;
    private Long customerId;
    private BigDecimal totalPrice;
    private String address;
    private String description;
}

package com.apigateway.aggregation.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MyCartResponse {
    private Long itemId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private boolean selected;
}

package com.apigateway.aggregation.client.dto.item.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ItemRegisterRequest {
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
}

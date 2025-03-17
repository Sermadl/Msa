package com.apigateway.aggregation.client.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Long userId;
}

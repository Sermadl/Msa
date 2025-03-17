package com.apigateway.aggregation.client.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PurchaseItemRequest {
    private Long itemId;
    private BigDecimal price;
    private int quantity;
}

package com.apigateway.aggregation.client.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponse {

    private Long itemId;
    private int quantity;
    private boolean selected;
}
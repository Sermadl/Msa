package com.apigateway.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private int quantity;
    private Long sellerId;

    public Item(String name, String description, int quantity, Long sellerId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }
}

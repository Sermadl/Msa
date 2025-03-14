package com.bff.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private Long itemId;
    private int quantity;
    private String address;
    private String description;

    public Orders(Long customerId, Long itemId, int quantity, String address, String description) {
        this.orderDate = LocalDateTime.now();
        this.customerId = customerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.address = address;
        this.description = description;
    }
}

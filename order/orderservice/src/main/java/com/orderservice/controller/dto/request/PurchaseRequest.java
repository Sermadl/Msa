package com.orderservice.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class PurchaseRequest {
    private Long customerId;
    private String address;
    private String description;
    private List<PurchaseItemRequest> itemList;
    private BigDecimal totalPrice;
}

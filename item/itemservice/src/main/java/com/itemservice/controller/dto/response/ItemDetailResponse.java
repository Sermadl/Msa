package com.itemservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ItemDetailResponse {
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Long sellerId;
    private Long categoryId;
}

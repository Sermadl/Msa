package com.itemservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private BigDecimal price;
}

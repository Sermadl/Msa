package com.itemservice.controller.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRegisterRequest {
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
}

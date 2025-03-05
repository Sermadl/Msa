package com.itemservice.controller.dto;

import lombok.Data;

@Data
public class ItemRegisterRequest {
    private String name;
    private String description;
    private int quantity;
    private Long sellerId;
}

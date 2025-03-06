package com.userserver.item.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserItemResponse {
    private Long id;
    private String name;
    private String description;
    private int quantity;
}

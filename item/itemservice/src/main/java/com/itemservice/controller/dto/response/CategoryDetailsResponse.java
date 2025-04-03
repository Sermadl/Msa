package com.itemservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryDetailsResponse {
    private List<String> smallCategories;
}

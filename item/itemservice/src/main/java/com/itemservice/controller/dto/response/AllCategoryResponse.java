package com.itemservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllCategoryResponse {
    private Long id;
    private String largeCategory;
    private List<CategoryDetailsResponse> smallCategories;
}

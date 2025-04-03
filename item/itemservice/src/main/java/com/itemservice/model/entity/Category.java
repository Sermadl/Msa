package com.itemservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private Long id;
    private String name;
    private Long parentId;

    public Category(String name) {
        this.name = name;
        this.parentId = null;
    }

    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}

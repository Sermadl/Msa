package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    private Long id;
    private Long userId;

    public Cart(Long userId) {
        this.userId = userId;
    }
}

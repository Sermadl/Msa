package com.itemservice.model.entity;

import com.itemservice.global.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item extends BaseEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Long sellerId;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String name, String description, int quantity, BigDecimal price, Long sellerId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.sellerId = sellerId;
    }
}

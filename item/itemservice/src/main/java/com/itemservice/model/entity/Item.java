package com.itemservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private Long sellerId;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String name, String description, int quantity, Long sellerId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }
}

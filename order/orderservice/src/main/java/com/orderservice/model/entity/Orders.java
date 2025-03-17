package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long customerId;
    private BigDecimal totalPrice;
    private String address;
    private String description;

    public Orders(Long customerId, BigDecimal totalPrice, String address, String description) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.address = address;
        this.description = description;
    }
}

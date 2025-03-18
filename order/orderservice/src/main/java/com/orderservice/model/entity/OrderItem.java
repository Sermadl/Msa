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
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Orders order;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Long itemId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime arrivalTime;

    public OrderItem(Orders order, Long itemId, String name, int quantity, BigDecimal price) {
        this.order = order;
        this.status = Status.PENDING;
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.arrivalTime = LocalDateTime.now().plusDays(1);
    }
}

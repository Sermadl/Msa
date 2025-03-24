package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    private String id;

    @ManyToOne
    private Orders order;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Long itemId;
    private Long sellerId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime arrivalTime;

    public OrderItem(Orders order, Long itemId, Long sellerId, String name, int quantity, BigDecimal price) {
        this.id = generateShortId();
        this.order = order;
        this.status = Status.PENDING;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.arrivalTime = LocalDateTime.now().plusDays(1);
    }

    public String generateShortId() {
        String timePart = String.valueOf(System.currentTimeMillis()).substring(8); // 시간 기반 3자리
        int randomPart = new Random().nextInt(90) + 10; // 10~99 두 자리 랜덤
        return timePart + randomPart;
    }

}

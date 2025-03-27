package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity implements Persistable<String> {

    @Id
    private String id;

    private String orderId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long itemId;
    private Long sellerId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime arrivalTime;

    @Transient
    private boolean isNew;

    public OrderItem(String orderId, Long itemId, Long sellerId, String name, int quantity, BigDecimal price) {
        this.id = generateShortId();
        this.orderId = orderId;
        this.status = Status.PENDING;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.arrivalTime = LocalDateTime.now().plusDays(1);
        this.isNew = true;
    }

    public String generateShortId() {
        String timePart = String.valueOf(System.currentTimeMillis()).substring(8); // 시간 기반 3자리
        int randomPart = new Random().nextInt(90) + 10; // 10~99 두 자리 랜덤
        return timePart + randomPart;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}

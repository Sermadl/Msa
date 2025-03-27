package com.orderservice.model.entity;

import com.orderservice.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {

    @Id
    private String id;
    private Long customerId;
    private BigDecimal totalPrice;
    private String address;
    private String description;

    public Orders(Long customerId, BigDecimal totalPrice, String address, String description) {
        this.id = generateOrderNumber();
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.address = address;
        this.description = description;
    }

    private static String lastDate = getCurrentDate();

    public String generateOrderNumber() {
        String currentDate = getCurrentDate();

        if (!lastDate.equals(currentDate)) {
            lastDate = currentDate;
        }

        String uuid = UUID.randomUUID().toString();
        int hash = Math.abs(uuid.hashCode());
        String numberPart = String.format("%06d", hash % 1000000);

        return currentDate + numberPart;
    }

    private static String getCurrentDate() {
        return new SimpleDateFormat("yyMMdd").format(new Date());
    }
}

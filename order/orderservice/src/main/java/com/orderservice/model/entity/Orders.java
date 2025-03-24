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

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {

    @Id
    private String id;
    private Long customerId;
    private Long sellerId;
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

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static String lastDate = getCurrentDate();

    public String generateOrderNumber() {
        String currentDate = getCurrentDate();

        // 🔍 날짜가 변경되면 카운터 초기화
        if (!lastDate.equals(currentDate)) {
            counter.set(0);
            lastDate = currentDate;
        }

        // ✅ 4자리 숫자 생성 (0000 ~ 9999)
        int nextNumber = counter.incrementAndGet();
        String numberPart = String.format("%04d", nextNumber);

        return currentDate + numberPart;
    }

    private static String getCurrentDate() {
        return new SimpleDateFormat("yyMMdd").format(new Date());
    }
}

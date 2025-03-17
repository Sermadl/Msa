package com.orderservice.model.entity;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("수락대기"),
    APPROVED("수락됨"),
    REJECTED("반려됨"),
    CANCELED("주문취소"),
    COMPLETED("배송완료");

    private final String status;

    Status (String status) {
        this.status = status;
    }
}

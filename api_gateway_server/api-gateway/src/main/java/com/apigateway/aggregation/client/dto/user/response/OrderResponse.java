package com.apigateway.aggregation.client.dto.user.response;

import com.apigateway.aggregation.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private LocalDateTime orderDate;
    private List<OrderItem> orderItemResponses;
    private String address;
    private String description;
    private BigDecimal totalPrice;
}

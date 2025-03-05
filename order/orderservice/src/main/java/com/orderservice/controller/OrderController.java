package com.orderservice.controller;

import com.orderservice.controller.dto.PurchaseRequest;
import com.orderservice.model.entity.Orders;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @MutationMapping
    public Orders purchase(@Argument("request") PurchaseRequest request) {
        return orderService.register(request);
    }
}

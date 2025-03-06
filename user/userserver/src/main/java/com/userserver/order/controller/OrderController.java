package com.userserver.order.controller;

import com.userserver.order.controller.dto.UserOrderResponse;
import com.userserver.order.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final UserOrderService userOrderService;

    @QueryMapping
    public List<UserOrderResponse> getUserOrders(@Argument Long userId) {
        return userOrderService.findUserOrder(userId);
    }
}

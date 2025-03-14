package com.bff.graphql.client;

import com.bff.graphql.model.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @GetMapping("/orders")
    List<Orders> getAllOrders();

    @GetMapping("/orders/user/{userId}")
    List<Orders> getOrdersByUserId(@PathVariable("userId") String userId);
}


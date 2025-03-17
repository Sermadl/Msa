package com.orderservice.controller;

import com.orderservice.controller.dto.request.PurchaseRequest;
import com.orderservice.controller.dto.response.OrderResponse;
import com.orderservice.model.entity.Orders;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(
                orderService.getAll()
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(
            @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok(
                orderService.getOrder(orderId)
        );
    }

    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<OrderResponse>> getPurchaseList(
            @PathVariable("customerId") Long customerId
    ) {
        return ResponseEntity.ok(
                orderService.getOrderByCustomerId(customerId)
        );
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> purchase(
            @RequestBody PurchaseRequest request
    ) {
        return ResponseEntity.ok(
                orderService.register(request)
        );
    }

}

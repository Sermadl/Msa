package com.orderservice.repository;

import com.orderservice.model.entity.OrderItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderItemRepository extends R2dbcRepository<OrderItem, Long> {
    Flux<OrderItem> findByOrderId(String orderId);
    Flux<OrderItem> findByItemId(Long itemId);
    Mono<OrderItem> findById(String id);
    Flux<OrderItem> findBySellerId(Long sellerId);
}

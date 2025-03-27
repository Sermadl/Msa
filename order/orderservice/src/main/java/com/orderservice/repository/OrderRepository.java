package com.orderservice.repository;

import com.orderservice.model.entity.Orders;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends R2dbcRepository<Orders, Long> {
    Flux<Orders> findByCustomerId(Long customerId);
    Mono<Orders> findById(String id);
}

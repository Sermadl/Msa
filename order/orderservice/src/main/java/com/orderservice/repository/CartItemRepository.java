package com.orderservice.repository;

import com.orderservice.model.entity.CartItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CartItemRepository extends R2dbcRepository<CartItem, String> {
    Flux<CartItem> findAllByCartId(Long cartId);
}

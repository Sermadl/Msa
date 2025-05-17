package com.orderservice.repository;

import com.orderservice.model.entity.CartItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemRepository extends R2dbcRepository<CartItem, String> {
    Flux<CartItem> findAllByCartIdOrderByUpdatedAtDesc(Long cartId);

    Mono<CartItem> findByCartIdAndItemId(Long id, Long itemId);

    Mono<Boolean> deleteByItemId(Long itemId);
}

package com.itemservice.repository;

import com.itemservice.model.entity.Item;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ItemRepository extends R2dbcRepository<Item, Long> {
    @Query("SELECT * FROM item WHERE id = :id FOR UPDATE")
    Mono<Item> findByIdForUpdate(Long id);
}

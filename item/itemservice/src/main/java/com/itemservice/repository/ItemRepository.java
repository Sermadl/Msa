package com.itemservice.repository;

import com.itemservice.controller.dto.response.ItemResponse;
import com.itemservice.model.entity.Item;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ItemRepository extends R2dbcRepository<Item, Long> {
    @Query("SELECT * FROM item WHERE id = :id FOR UPDATE")
    Mono<Item> findByIdForUpdate(Long id);

    Flux<Item> findByCategoryId(Long categoryId);

    @Query("SELECT * FROM item WHERE category_id IN (:categoryIds)")
    Flux<Item> findByCategoryIdIn(List<Long> categoryIds);
}

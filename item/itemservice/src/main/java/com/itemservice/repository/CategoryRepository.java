package com.itemservice.repository;

import com.itemservice.model.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CategoryRepository extends R2dbcRepository<Category, Long> {
    Flux<Category> findByParentIdIsNull();
    Flux<Category> findByParentId(Long parentId);
}

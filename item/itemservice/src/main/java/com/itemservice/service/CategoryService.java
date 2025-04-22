package com.itemservice.service;

import com.itemservice.controller.dto.request.CategoryRegisterRequest;
import com.itemservice.controller.dto.response.AllCategoryResponse;
import com.itemservice.controller.dto.response.CategoryDetailsResponse;
import com.itemservice.global.util.RoleCheck;
import com.itemservice.global.util.UserRole;
import com.itemservice.model.entity.Category;
import com.itemservice.repository.CategoryRepository;
import com.itemservice.service.error.CategoryNotFoundException;
import com.itemservice.service.error.CategoryRegisterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Flux<AllCategoryResponse> allCategories() {
        return categoryRepository.findByParentIdIsNull()
                .flatMap(this::getAllCategory);
    }

//    public CategoryDetailsResponse getCategoryDetails(Long id) {
//        return categoryRepository.findByParentId(id)
//                .map(Category::getName)
//                .collectList()
//                .map(CategoryDetailsResponse::new);
//    }

    public Flux<CategoryDetailsResponse> getCategoryDetails(Long id) {
        return categoryRepository.findByParentId(id)
                .map(category -> new CategoryDetailsResponse(
                        category.getId(),
                        category.getName()
                ));
    }

    public Mono<Void> register(
            CategoryRegisterRequest request,
            Long userId,
            UserRole role
    ) {
        log.info("user({}) registering new category: {}", userId, request.getName());

        RoleCheck.isAdmin(role);

        Mono<Category> categoryMono;

        if (request.getParentId() == null) {
            categoryMono = Mono.just(new Category(request.getName()));
        } else {
            categoryMono = categoryRepository.findById(request.getParentId())
                    .switchIfEmpty(Mono.error(new CategoryNotFoundException()))
                    .map(parent -> new Category(request.getName(), parent.getId()));
        }

        return categoryMono
                .flatMap(categoryRepository::save)
                .switchIfEmpty(Mono.error(new CategoryRegisterException()))
                .then();
    }

    private Mono<AllCategoryResponse> getAllCategory(Category category) {
        return categoryRepository.findByParentId(category.getId())
                .map(child ->
                        new CategoryDetailsResponse(
                                child.getId(),
                                child.getName()
                        )
                )
                .collectList()
                .map(children ->
                        new AllCategoryResponse(
                                category.getId(),
                                category.getName(),
                                children
                        )
                );
    }

}

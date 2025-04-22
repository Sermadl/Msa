package com.itemservice.service;

import com.itemservice.controller.dto.request.ItemRegisterRequest;
import com.itemservice.controller.dto.response.ItemResponse;
import com.itemservice.model.entity.Category;
import com.itemservice.model.entity.Item;
import com.itemservice.repository.CategoryRepository;
import com.itemservice.repository.ItemRepository;
import com.itemservice.service.error.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public Flux<ItemResponse> getAll() {
        return itemRepository.findAll()
                .map(this::getItemResponse);
    }

    public Mono<ItemResponse> register(ItemRegisterRequest request, Long sellerId) {
        Item item = new Item(
                request.getName(),
                request.getDescription(),
                request.getQuantity(),
                request.getPrice(),
                sellerId,
                request.getCategoryId()
        );

        return itemRepository.save(item)
                .map(this::getItemResponse);
    }

    public Mono<ItemResponse> getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(new ItemNotFoundException()))
                .map(this::getItemResponse);
    }

    public Flux<ItemResponse> getItemByCategory(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId)
                .map(this::getItemResponse);
    }

    public Flux<ItemResponse> getItemByLargeCategory(Long categoryId) {
        return getSubCategoryIds(categoryId)
                .flatMapMany(itemRepository::findByCategoryIdIn)
                .map(this::getItemResponse);
    }

    private ItemResponse getItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                item.getSellerId(),
                item.getCategoryId()
        );
    }

    public Mono<List<Long>> getSubCategoryIds(Long parentId) {
        return categoryRepository.findByParentId(parentId)
                .map(Category::getId)
                .collectList()
                .map(ids -> {
                    ids.add(parentId); // 상위도 포함
                    return ids;
                });
    }
}

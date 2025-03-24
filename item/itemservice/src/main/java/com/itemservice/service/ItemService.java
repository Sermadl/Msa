package com.itemservice.service;

import com.itemservice.controller.dto.request.ItemRegisterRequest;
import com.itemservice.controller.dto.response.ItemResponse;
import com.itemservice.model.entity.Item;
import com.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemResponse> getAll() {
        List<Item> items = itemRepository.findAll();

        return getAllItemResponse(items);
    }

    public ItemResponse register(ItemRegisterRequest request, Long sellerId) {
        Item item = new Item(
                request.getName(),
                request.getDescription(),
                request.getQuantity(),
                request.getPrice(),
                sellerId
        );

        return getItemResponse(
                itemRepository.save(item)
        );
    }

    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        return getItemResponse(item);
    }

    private ItemResponse getItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                item.getSellerId()
        );
    }

    private List<ItemResponse> getAllItemResponse(List<Item> items) {
        return items.stream().map(
                item -> new ItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSellerId()
                )
        ).toList();
    }
}

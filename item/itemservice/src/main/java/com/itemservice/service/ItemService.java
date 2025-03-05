package com.itemservice.service;

import com.itemservice.controller.dto.ItemRegisterRequest;
import com.itemservice.model.entity.Item;
import com.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item register(ItemRegisterRequest arg) {
        Item item = new Item(
                arg.getName(),
                arg.getDescription(),
                arg.getQuantity(),
                arg.getSellerId()
        );

        return itemRepository.save(item);
    }
}

package com.userserver.item.service;

import com.userserver.item.controller.dto.UserItemResponse;
import com.userserver.item.model.entity.Item;
import com.userserver.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserItemService {
    private final ItemRepository itemRepository;

    public List<UserItemResponse> getUserItems(Long userId) {
        List<Item> items = itemRepository.findBySellerId(userId);

        return items.stream().map(
                item -> new UserItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getQuantity()
                )
        ).toList();
    }
}

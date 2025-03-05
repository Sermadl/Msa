package com.itemservice.controller;

import com.itemservice.controller.dto.ItemRegisterRequest;
import com.itemservice.model.entity.Item;
import com.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @MutationMapping
    public Item registerItem(@Argument("request") ItemRegisterRequest request) {
        return itemService.register(request);
    }
}

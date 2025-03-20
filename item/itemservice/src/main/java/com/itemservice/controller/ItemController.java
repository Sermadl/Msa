package com.itemservice.controller;

import com.itemservice.controller.dto.request.ItemRegisterRequest;
import com.itemservice.controller.dto.response.ItemResponse;
import com.itemservice.service.ItemService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/list")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAll());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.getItem(itemId));
    }

    @PostMapping("/register")
    public ResponseEntity<ItemResponse> registerItem(@RequestBody ItemRegisterRequest request) {
        return ResponseEntity.ok(itemService.register(request));
    }
}

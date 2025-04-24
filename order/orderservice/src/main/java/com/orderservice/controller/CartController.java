package com.orderservice.controller;

import com.orderservice.controller.dto.request.CartRequest;
import com.orderservice.controller.dto.response.CartItemResponse;
import com.orderservice.global.util.RoleCheck;
import com.orderservice.global.util.UserRole;
import com.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@Slf4j
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/my")
    public Flux<CartItemResponse> myCart(
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isUser(role);

        return cartService.getMyCart(userId);
    }

    @PostMapping()
    public Mono<CartItemResponse> addCart(
            @RequestBody CartRequest request,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isUser(role);

        return cartService.addToCart(userId, request);
    }

    @PostMapping("/init")
    public Mono<Void> initCart(
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isUser(role);

        return cartService.initCart(userId);
    }
}

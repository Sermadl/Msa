package com.orderservice.service;

import com.orderservice.controller.dto.request.CartRequest;
import com.orderservice.controller.dto.response.CartItemResponse;
import com.orderservice.model.entity.Cart;
import com.orderservice.model.entity.CartItem;
import com.orderservice.repository.CartItemRepository;
import com.orderservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Mono<CartItemResponse> addToCart(Long userId, CartRequest request) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.defer(() -> {
                    Cart cart = new Cart(userId);
                    return cartRepository.save(cart);
                }))
                .flatMap(cart -> {
                    CartItem cartItem = new CartItem(
                            cart.getId(),
                            request.getItemId(),
                            request.getQuantity(),
                            true
                    );
                    return cartItemRepository.save(cartItem);
                })
                .flatMap(this::getCartItem);
    }

    public Flux<CartItemResponse> getMyCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .flatMapMany(cart ->
                    cartItemRepository.findAllByCartId(cart.getId())
                )
                .flatMap(this::getCartItem);
    }
    public Mono<CartItemResponse> getCartItem(CartItem cartItem) {
        return Mono.just(
        new CartItemResponse(
        cartItem.getItemId(),
        cartItem.getQuantity(),
        cartItem.isSelected()
                )
        );
    }

    public Mono<Void> initCart(Long userId) {
        Cart cart = new Cart(userId);
        return cartRepository.save(cart).then();
    }
}

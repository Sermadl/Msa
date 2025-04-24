package com.orderservice.service;

import com.orderservice.controller.dto.request.CartRequest;
import com.orderservice.controller.dto.response.CartItemResponse;
import com.orderservice.model.entity.Cart;
import com.orderservice.model.entity.CartItem;
import com.orderservice.repository.CartItemRepository;
import com.orderservice.repository.CartRepository;
import com.orderservice.service.error.CartItemNotFound;
import com.orderservice.service.error.CartNotFoundException;
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
                .flatMap(cart ->
                    cartItemRepository.findByCartIdAndItemId(cart.getId(), request.getItemId())
                            .flatMap(existingItem -> {
                                // 기존 아이템이 있으면 수량만 누적
                                existingItem.increaseQuantity();
                                return cartItemRepository.save(existingItem);
                            })
                            .switchIfEmpty(
                                    // 없으면 새로 추가
                                    cartItemRepository.save(new CartItem(
                                            cart.getId(),
                                            request.getItemId(),
                                            request.getQuantity(),
                                            true
                                    ))
                            )
                )
                .flatMap(this::getCartItem);
    }

    public Flux<CartItemResponse> getMyCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CartNotFoundException()))
                .flatMapMany(cart ->
                    cartItemRepository.findAllByCartIdOrderByUpdatedAtDesc(cart.getId())
                )
                .concatMap(this::getCartItem);
    }

    public Mono<Void> initCart(Long userId) {
        Cart cart = new Cart(userId);
        return cartRepository.save(cart).then();
    }

    public Mono<CartItemResponse> addItem(Long userId, Long itemId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CartNotFoundException()))
                .flatMap(cart ->
                        cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                                .switchIfEmpty(Mono.error(new CartItemNotFound()))
                                .flatMap(cartItem -> {
                                    cartItem.increaseQuantity();
                                    return cartItemRepository.save(cartItem);
                                })
                )
                .flatMap(this::getCartItem);
    }

    public Mono<CartItemResponse> decreaseItem(Long userId, Long itemId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CartNotFoundException()))
                .flatMap(cart ->
                        cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                                .switchIfEmpty(Mono.error(new CartItemNotFound()))
                                .flatMap(cartItem -> {
                                    cartItem.decreaseQuantity();
                                    if (cartItem.getQuantity() <= 0) {
                                        return cartItemRepository.delete(cartItem)
                                                .thenReturn(new CartItem(
                                                        null, cart.getId(), itemId,0, false
                                                ));
                                    } else {
                                        return cartItemRepository.save(cartItem);
                                    }
                                })
                )
                .flatMap(this::getCartItem);
    }

    public Mono<CartItemResponse> selectItem(Long userId, Long itemId, boolean select) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new CartNotFoundException()))
                .flatMap(cart ->
                        cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                                .switchIfEmpty(Mono.error(new CartItemNotFound()))
                                .flatMap(cartItem -> {
                                    cartItem.setSelected(select);

                                    return cartItemRepository.save(cartItem);
                                })
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
}

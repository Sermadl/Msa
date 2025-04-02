package com.orderservice.service;

import com.orderservice.controller.dto.request.PurchaseItemRequest;
import com.orderservice.controller.dto.request.PurchaseRequest;
import com.orderservice.controller.dto.response.OrderItemResponse;
import com.orderservice.controller.dto.response.OrderResponse;
import com.orderservice.controller.dto.response.OrderSellerResponse;
import com.orderservice.global.kafka.KafkaProducer;
import com.orderservice.global.util.UserRole;
import com.orderservice.global.util.error.HasNoAuthorityException;
import com.orderservice.model.entity.OrderItem;
import com.orderservice.model.entity.Orders;
import com.orderservice.repository.OrderItemRepository;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.error.OrderItemNotFoundException;
import com.orderservice.service.error.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;
    private final OrderItemRepository orderItemRepository;

    public Flux<OrderResponse> getAll() {
        return orderRepository.findAll()
                .flatMap(this::getOrderResponse);
    }

    public Mono<OrderResponse> getUserOrder(Long userId, String id, UserRole role) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException()))
                .flatMap(order -> {
                    if (!order.getCustomerId().equals(userId) && !role.isAdmin()) {
                        return Mono.error(new HasNoAuthorityException());
                    }

                    return getOrderResponse(order);
                });
    }

    public Mono<OrderSellerResponse> getSellerOrder(Long sellerId, String orderItemId, UserRole role) {
        return orderItemRepository.findById(orderItemId)
                .switchIfEmpty(Mono.error(new OrderItemNotFoundException()))
                .flatMap(orderItem -> {
                    if (!orderItem.getSellerId().equals(sellerId) && !role.isAdmin()) {
                        log.info("user id({}) accessed to get seller({})'s orders", sellerId, orderItem.getSellerId());
                        return Mono.error(new HasNoAuthorityException());
                    }
                    return getOrderSellerResponse(orderItem);
                });
    }

    public Flux<OrderResponse> getOrderByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .flatMap(this::getOrderResponse);
    }

    public Flux<OrderSellerResponse> getOrderBySellerId(Long sellerId) {
        return orderItemRepository.findBySellerId(sellerId)
                .flatMap(this::getOrderSellerResponse);
    }

    public Flux<OrderSellerResponse> getOrderBySellerIdAndItemId(Long sellerId, UserRole role, Long itemId) {
        return orderItemRepository.findByItemId(itemId)
                .collectList()
                .flatMapMany(orders -> {
                    if (!orders.isEmpty()
                            && !orders.get(0).getSellerId().equals(sellerId)
                            && !role.isAdmin()) {
                        return Mono.error(new HasNoAuthorityException());
                    }

                    return Flux.fromIterable(orders)
                            .flatMap(this::getOrderSellerResponse);
                });
    }

    public Mono<OrderResponse> register(PurchaseRequest request, Long userId) {
        List<PurchaseItemRequest> itemRequests = request.getItemList();

        Orders orders = new Orders(
                userId,
                request.getTotalPrice(),
                request.getAddress(),
                request.getDescription()
        );

        return orderRepository.save(orders)
                .flatMap(order ->
                    registerItem(itemRequests, order)
                            .then(getOrderResponse(order))
                );
    }

    private Mono<Void> registerItem(List<PurchaseItemRequest> requests,
                              Orders order) {
        return Flux.fromIterable(requests)
                .flatMap(request -> {
                    OrderItem orderItem = new OrderItem(
                            order.getId(),
                            request.getItemId(),
                            request.getSellerId(),
                            request.getName(),
                            request.getQuantity(),
                            request.getPrice()
                    );

                    return orderItemRepository.save(orderItem)
                            .doOnSuccess(kafkaProducer::sendDbUpdateMessage);
                })
                .then();

    }

    private Mono<OrderResponse> getOrderResponse(Orders order) {
         return orderItemRepository.findByOrderId(order.getId())
                 .map(this::getOrderItemResponse)
                 .collectList()
                 .map(orderItems ->
                     new OrderResponse(
                             order.getId(),
                             order.getCreatedAt(),
                             orderItems,
                             order.getAddress(),
                             order.getDescription(),
                             order.getTotalPrice()
                     )
                 );
    }

    private OrderItemResponse getOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getOrderId(),
                orderItem.getItemId(),
                orderItem.getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getStatus(),
                orderItem.getArrivalTime()
        );
    }

    private Mono<OrderSellerResponse> getOrderSellerResponse(OrderItem orderItem) {
        return orderRepository.findById(orderItem.getOrderId())
                .map(order ->
                        new OrderSellerResponse(
                            orderItem.getId(),
                            order.getCreatedAt(),
                            order.getCustomerId(),
                            order.getAddress(),
                            order.getDescription(),
                            orderItem.getPrice(),
                            orderItem.getQuantity(),
                            orderItem.getStatus(),
                            orderItem.getArrivalTime()
                        )
                );
    }
}

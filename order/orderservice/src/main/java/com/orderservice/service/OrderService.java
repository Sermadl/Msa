package com.orderservice.service;

import com.orderservice.controller.dto.request.PurchaseItemRequest;
import com.orderservice.controller.dto.request.PurchaseRequest;
import com.orderservice.controller.dto.response.OrderItemResponse;
import com.orderservice.controller.dto.response.OrderResponse;
import com.orderservice.global.kafka.KafkaProducer;
import com.orderservice.global.util.error.HasNoAuthorityException;
import com.orderservice.model.entity.OrderItem;
import com.orderservice.model.entity.Orders;
import com.orderservice.repository.OrderItemRepository;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;
    private final OrderItemRepository orderItemRepository;

    public List<OrderResponse> getAll() {
        List<Orders> orders = orderRepository.findAll();

        return getOrderResponseList(orders);
    }

    public OrderResponse getUserOrder(Long userId, Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getCustomerId().equals(userId)) {
            throw new HasNoAuthorityException();
        }

        return getOrderResponse(order);
    }

    public OrderResponse getSellerOrder(Long sellerId, Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getSellerId().equals(sellerId)) {
            throw new HasNoAuthorityException();
        }

        return getOrderResponse(order);
    }

    public List<OrderResponse> getOrderByCustomerId(Long customerId) {
        List<Orders> orders = orderRepository.findByCustomerId(customerId);

        return getOrderResponseList(orders);
    }

    public List<OrderResponse> getOrderBySellerId(Long sellerId) {
        List<Orders> orders = orderRepository.findBySellerId(sellerId);

        return getOrderResponseList(orders);
    }

    public List<OrderResponse> getOrderBySellerIdAndItemId(Long sellerId, Long itemId) {
        List<OrderItem> orderItems = orderItemRepository.findByItemId(itemId);

        List<Orders> orders = orderItems.stream()
                .map(OrderItem::getOrder)
                .filter(order -> order.getSellerId().equals(sellerId))
                .toList();

        return getOrderResponseList(orders);
    }

    public OrderResponse register(PurchaseRequest request, Long userId) {
        List<PurchaseItemRequest> itemRequests = request.getItemList();

        Orders order = new Orders(
                userId,
                request.getTotalPrice(),
                request.getAddress(),
                request.getDescription()
        );

        order = orderRepository.save(order);

        registerItem(itemRequests, order);

        return getOrderResponse(order);
    }

    private void registerItem(List<PurchaseItemRequest> requests,
                              Orders order) {
        requests.forEach(
                request -> {
                    OrderItem orderItem = new OrderItem(
                            order,
                            request.getItemId(),
                            request.getName(),
                            request.getQuantity(),
                            request.getPrice()
                    );

                    kafkaProducer.sendDbUpdateMessage(
                            orderItemRepository.save(orderItem)
                    );
                }
        );
    }

    private List<OrderResponse> getOrderResponseList(List<Orders> orders) {

        return orders.stream().map(
                order -> new OrderResponse(
                        order.getId(),
                        order.getCreatedAt(),
                        getOrderItemList(
                                orderItemRepository.findByOrderId(order.getId())
                        ),
                        order.getAddress(),
                        order.getDescription(),
                        order.getTotalPrice()
                )
        ).toList();
    }

    private OrderResponse getOrderResponse(Orders order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        return new OrderResponse(
                order.getId(),
                order.getCreatedAt(),
                getOrderItemList(orderItems),
                order.getAddress(),
                order.getDescription(),
                order.getTotalPrice()
        );
    }

    private List<OrderItemResponse> getOrderItemList(List<OrderItem> orderItems) {
        return orderItems.stream().map(
                orderItem -> new OrderItemResponse(
                        orderItem.getId(),
                        orderItem.getOrder().getId(),
                        orderItem.getItemId(),
                        orderItem.getName(),
                        orderItem.getPrice(),
                        orderItem.getQuantity(),
                        orderItem.getStatus(),
                        orderItem.getArrivalTime()
                )
        ).toList();
    }
}

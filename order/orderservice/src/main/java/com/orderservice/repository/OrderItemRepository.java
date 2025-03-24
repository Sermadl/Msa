package com.orderservice.repository;

import com.orderservice.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(String orderId);
    List<OrderItem> findByItemId(Long itemId);
    Optional<OrderItem> findById(String id);
    List<OrderItem> findBySellerId(Long sellerId);
    List<OrderItem> findBySellerIdAndItemId(Long sellerId, Long itemId);
}

package com.orderservice.repository;

import com.orderservice.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(String orderId);
    List<OrderItem> findByItemId(Long itemId);
}

package com.userserver.order.service;

import com.userserver.order.controller.dto.UserOrderResponse;
import com.userserver.order.model.entity.Orders;
import com.userserver.order.repository.OrderRepository;
import com.userserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public List<UserOrderResponse> findUserOrder(Long userId) {
        List<Orders> orders = orderRepository.findByCustomerId(userId);

        return orders.stream().map(
                order -> new UserOrderResponse(
                    order.getId(),
                    order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    order.getQuantity(),
                    order.getAddress(),
                    order.getDescription()
                )
        ).toList();
    }
}

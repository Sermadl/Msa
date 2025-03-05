package com.orderservice.service;

import com.orderservice.controller.dto.PurchaseRequest;
import com.orderservice.global.kafka.KafkaProducer;
import com.orderservice.model.entity.Orders;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    public Orders register(PurchaseRequest arg) {
        Orders order = new Orders(
                arg.getCustomerId(),
                arg.getItemId(),
                arg.getQuantity(),
                arg.getAddress(),
                arg.getDescription()
        );

        kafkaProducer.sendDbUpdateMessage(order);
        return orderRepository.save(order);
    }
}

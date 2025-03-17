package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.OrderServiceClient;
import com.apigateway.aggregation.client.dto.user.request.PurchaseRequest;
import com.apigateway.aggregation.client.dto.user.response.OrderResponse;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceClient orderServiceClient;

    @QueryMapping
    public Flux<OrderResponse> getAllOrders() {

        log.info("Get All Orders");

        Flux<OrderResponse> response = orderServiceClient.getAllOrders();

        return response;
    }

    @QueryMapping
    public Mono<OrderResponse> getOrder(@Argument("orderId") Long orderId) {
        log.info("Get Order: {}", orderId);

        Mono<OrderResponse> response = orderServiceClient.getOrder(orderId);

        log.info(response.toString());

        return response;
    }

    @QueryMapping
    public Flux<OrderResponse> getPurchaseList(@Argument("customerId") Long customerId) {
        log.info("Get Purchase List: {}", customerId);

        Flux<OrderResponse> response = orderServiceClient.getPurchaseList(customerId);

        return response;
    }

    @MutationMapping
    public Mono<OrderResponse> registerOrder(@Argument("request") PurchaseRequest request) {
        log.info("Register Order: {}", request);

        Mono<OrderResponse> response = orderServiceClient.purchase(request);

        log.info(response.toString());

        return response;
    }
}

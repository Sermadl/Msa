package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.ItemServiceClient;
import com.apigateway.aggregation.client.OrderServiceClient;
import com.apigateway.aggregation.client.UserServiceClient;
import com.apigateway.aggregation.client.dto.item.response.ItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderResponse;
import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import com.apigateway.aggregation.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AggregationController {
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final ItemServiceClient itemServiceClient;

    @GetMapping("/my-page")
    public Mono<List<ItemResponse>> getMyPage(ServerWebExchange e) {

        ValidTokenResponse response = userServiceClient.tokenValidation(e);

        log.info("User Info: {}", response.getId());
        log.info("{}", response.getRole());
        log.info("{}", response.getUsername());

        Mono<List<OrderResponse>> orderResponses = orderServiceClient.getPurchaseList(response.getId());

        // 3️⃣ 주문 목록에서 상품 ID만 추출 후, 상품 정보 조회
        return orderResponses
                .flatMapMany(Flux::fromIterable)
                .flatMap(order -> Flux.fromIterable(order.getOrderItemResponses()))
                .map(OrderItemResponse::getItemId)
                .distinct() // 중복된 아이템 제거
                .flatMap(itemServiceClient::getItem) // 각 아이템 정보 조회
                .collectList();
    }
}

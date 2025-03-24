package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.ItemServiceClient;
import com.apigateway.aggregation.client.OrderServiceClient;
import com.apigateway.aggregation.client.UserServiceClient;
import com.apigateway.aggregation.client.dto.item.response.ItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderResponse;
import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AggregationController {
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final ItemServiceClient itemServiceClient;

    @GetMapping("/my-page")
    public Flux<ItemResponse> getMyPage(ServerWebExchange e) {

        // 사용자 인증
        ValidTokenResponse response = userServiceClient.tokenValidation(e);

        log.info("User Info: {}", response.getId());
        log.info("{}", response.getRole());
        log.info("{}", response.getUsername());

        // 로그인 된 사용자의 주문 내역 불러오기
        // Todo
        //  최근 주문 내역으로 불러와야할듯?
        Flux<OrderResponse> orderResponses = orderServiceClient.getMyPurchaseList(
                response.getId(),
                response.getRole()
        );

        // 주문 목록에서 상품 ID만 추출 후, 상품 정보 조회
        return orderResponses
                .flatMap(order -> Flux.fromIterable(order.getOrderItemResponses()))
                .map(OrderItemResponse::getItemId)
                .distinct() // 중복된 아이템 제거
                .flatMap(itemServiceClient::getItemById); // 각 아이템 정보 조회
    }
}

package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.order.request.PurchaseRequest;
import com.apigateway.aggregation.client.dto.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Flux<OrderResponse> getAllOrders() {
        return webClientBuilder.build()
                .get()
                .uri("http://ORDER-SERVICE/list")
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    public Mono<OrderResponse> getOrder(Long orderId) {
        return webClientBuilder.build()
                .get()
                .uri("http://ORDER-SERVICE/{orderId}", orderId)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    public Flux<OrderResponse> getPurchaseList(Long customerId) {
        return webClientBuilder.build()
                .get()
                .uri("http://ORDER-SERVICE/list/{customerId}", customerId)
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    public Mono<OrderResponse> purchase(PurchaseRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://ORDER-SERVICE/order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

}


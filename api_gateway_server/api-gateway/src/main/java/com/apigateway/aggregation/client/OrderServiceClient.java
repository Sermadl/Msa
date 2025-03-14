package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Flux<UserInfoResponse> getAllUsers() {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user")
                .retrieve()
                .bodyToFlux(UserInfoResponse.class);
    }

//    @GetMapping("/orders")
//    List<Orders> getAllOrders();
//
//    @GetMapping("/orders/user/{userId}")
//    List<Orders> getOrdersByUserId(@PathVariable("userId") String userId);
}


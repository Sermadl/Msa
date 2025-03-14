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
public class ItemServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Flux<UserInfoResponse> getAllUsers() {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user")
                .retrieve()
                .bodyToFlux(UserInfoResponse.class);
    }

//    @GetMapping("/items")
//    List<Item> getAllItems();
//
//    @GetMapping("/items/user/{userId}")
//    List<Item> getItemsByUserId(@PathVariable("userId") String userId);
}

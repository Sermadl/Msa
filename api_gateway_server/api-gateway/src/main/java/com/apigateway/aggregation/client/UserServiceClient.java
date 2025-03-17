package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.user.request.RegisterUserRequest;
import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient.Builder webClientBuilder;

    public Flux<UserInfoResponse> getAllUsers() {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user")
                .retrieve()
                .bodyToFlux(UserInfoResponse.class);
    }

    public Mono<UserInfoResponse> getUser(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }

    public Mono<UserInfoResponse> registerUser(RegisterUserRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://USER-SERVICE/user")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }
}

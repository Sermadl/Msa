package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.user.request.LoginRequest;
import com.apigateway.aggregation.client.dto.user.request.RegisterUserRequest;
import com.apigateway.aggregation.client.dto.user.response.LoginResponse;
import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import com.apigateway.aggregation.controller.error.InvalidTokenException;
import com.apigateway.global.error.model.UnknownException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
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

    public Mono<LoginResponse> login(LoginRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://USER-SERVICE/login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LoginResponse.class);
    }

    public ValidTokenResponse tokenValidation(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        log.info("Header: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new InvalidTokenException();
        }

        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/validation")
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new InvalidTokenException();
                })
                .bodyToMono(ValidTokenResponse.class)
                .block();
    }
}

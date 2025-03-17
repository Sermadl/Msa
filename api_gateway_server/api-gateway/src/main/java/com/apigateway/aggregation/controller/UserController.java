package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.UserServiceClient;
import com.apigateway.aggregation.client.dto.user.request.RegisterUserRequest;
import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import graphql.kickstart.tools.GraphQLQueryResolver;
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
public class UserController implements GraphQLQueryResolver {

    private final UserServiceClient userServiceClient;

    @QueryMapping
    public Flux<UserInfoResponse> getAllUsers() {
        log.info("Get all users");

        Flux<UserInfoResponse> responses = userServiceClient.getAllUsers();

        log.info(responses.toString());

        return responses;
    }

    @QueryMapping
    public Mono<UserInfoResponse> getUser(@Argument("userId") Long userId) {
        log.info("Get user: {}", userId);

        Mono<UserInfoResponse> response = userServiceClient.getUser(userId);

        log.info(response.toString());

        return response;
    }

    @MutationMapping
    public Mono<UserInfoResponse> registerUser(
            @Argument("request") RegisterUserRequest request
    ) {
        log.info("Register user");

        Mono<UserInfoResponse> response = userServiceClient.registerUser(request);

        log.info(response.toString());

        return response;
    }
}

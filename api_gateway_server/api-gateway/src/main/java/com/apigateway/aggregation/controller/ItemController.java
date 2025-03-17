package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.ItemServiceClient;
import com.apigateway.aggregation.client.dto.user.request.ItemRegisterRequest;
import com.apigateway.aggregation.client.dto.user.response.ItemResponse;
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
public class ItemController implements GraphQLQueryResolver {

    private final ItemServiceClient itemServiceClient;

    @QueryMapping
    public Flux<ItemResponse> getAllItems() {
        log.info("Get All Items");

        Flux<ItemResponse> response = itemServiceClient.getAllItems();

        return response;
    }

    @QueryMapping
    public Mono<ItemResponse> getItem(@Argument("itemId") Long itemId) {
        log.info("Get Item: {}", itemId);

        Mono<ItemResponse> response = itemServiceClient.getItem(itemId);

        return response;
    }

    @MutationMapping
    public Mono<ItemResponse> registerItem(
            @Argument("request") ItemRegisterRequest request
    ) {
        log.info("Get user item register");

        Mono<ItemResponse> response = itemServiceClient.registerItem(request);

        log.info(response.toString());

        return response;
    }
}

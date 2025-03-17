package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.ItemServiceClient;
import com.apigateway.aggregation.client.OrderServiceClient;
import com.apigateway.aggregation.client.UserServiceClient;
import com.apigateway.aggregation.controller.dto.response.UserOrderItemResponse;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserItemController implements GraphQLQueryResolver {

    private final UserServiceClient userServiceClient;
    private final ItemServiceClient itemServiceClient;

//    @QueryMapping
//    public Flux<UserOrderItemResponse> getUserOrderItems(int page, int size) {
//
//
//        return
//    }
}


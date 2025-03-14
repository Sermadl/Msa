package com.apigateway.aggregation;

//import com.bff.graphql.client.ItemServiceClient;
//import com.bff.graphql.client.OrderServiceClient;
import com.bff.graphql.client.UserServiceClient;
import com.bff.graphql.client.dto.user.request.RegisterUserRequest;
import com.bff.graphql.client.dto.user.response.UserInfoResponse;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraphQLResolver implements GraphQLQueryResolver {

    private final UserServiceClient userServiceClient;
//    private final OrderServiceClient orderServiceClient;
//    private final ItemServiceClient itemServiceClient;

    @QueryMapping
    public Flux<UserInfoResponse> user() {
        log.info("Get all users");

        Flux<UserInfoResponse> responses = userServiceClient.getAllUsers();

        log.info(responses.toString());

        return responses;
    }

    @MutationMapping
    public Mono<UserInfoResponse> registerUser(
            @Argument("request") RegisterUserRequest request ) {
        log.info("Register user");

        Mono<UserInfoResponse> response = userServiceClient.registerUser(request);

        log.info(response.toString());

        return response;
    }

//    public List<Orders> allOrders() {
//        return orderServiceClient.getAllOrders();
//    }
}


//package com.bff.graphql;
//
//import com.bff.graphql.client.ItemServiceClient;
//import com.bff.graphql.client.OrderServiceClient;
//import com.bff.graphql.client.UserServiceClient;
//import com.bff.graphql.client.dto.user.response.UserInfoResponse;
//import com.bff.graphql.model.Orders;
//import com.bff.graphql.model.User;
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class GraphQLResolver implements GraphQLQueryResolver {
//
//    private final UserServiceClient userServiceClient;
//    private final OrderServiceClient orderServiceClient;
//    private final ItemServiceClient itemServiceClient;
//
//    public List<UserInfoResponse> allUsers() {
//        log.info("Get all users");
//        return userServiceClient.getAllUsers();
//    }
//
//    public List<Orders> allOrders() {
//        return orderServiceClient.getAllOrders();
//    }
//}
//

package com.bff.graphql.client;

import com.bff.graphql.client.dto.user.request.RegisterUserRequest;
import com.bff.graphql.client.dto.user.response.UserInfoResponse;
import com.bff.graphql.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @GetMapping("/user")
    List<UserInfoResponse> getAllUsers();

    @PostMapping("/user")
    UserInfoResponse createUser(@RequestBody RegisterUserRequest request);

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") String id);
}


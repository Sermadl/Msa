package com.apigateway.aggregation.controller;

import com.apigateway.aggregation.client.UserServiceClient;
import com.apigateway.aggregation.client.dto.user.request.LoginRequest;
import com.apigateway.aggregation.client.dto.user.request.RegisterUserRequest;
import com.apigateway.aggregation.client.dto.user.response.LoginResponse;
import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AggregationController {

}

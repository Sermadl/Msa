package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.user.request.LoginRequest;
import com.apigateway.aggregation.client.dto.user.request.RegisterUserRequest;
import com.apigateway.aggregation.client.dto.user.response.LoginResponse;
import com.apigateway.aggregation.client.dto.user.response.UserInfoResponse;
import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import com.apigateway.aggregation.controller.error.InvalidTokenException;
import com.apigateway.aggregation.model.UserRole;
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

    /** [관리자]
     * 모든 사용자 정보 가져오기
     * @param userId 로그인 된 사용자 Id
     * @param role 로그인 된 사용자 Role
     * @return 모든 사용자 정보 리스트
     */
    public Flux<UserInfoResponse> getAllUsers(Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/list")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(UserInfoResponse.class);
    }

    /** [관리자]
     * User ID로 사용자 찾기
     * @param targetId 찾을 사용자의 User Id
     * @param userId 로그인 된 사용자 Id
     * @param role 로그인 된 사용자 Role
     * @return 사용자 정보
     */
    public Mono<UserInfoResponse> getUserById(Long targetId, Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/{userId}", targetId)
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }

    /** [사용자]
     * 로그인 된 사용자 정보 불러오기
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 로그인 된 사용자 정보
     */
    public Mono<UserInfoResponse> getMyInfo(Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/my")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }

    /** [권한 제한 없음]
     * 회원가입하기
     * @param request 회원가입 정보
     * @return 등록된 사용자 정보
     */
    public Mono<UserInfoResponse> createUser(RegisterUserRequest request, Long userId, UserRole role) {
        return webClientBuilder.build()
                .post()
                .uri("http://USER-SERVICE/register")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }

    /** [사용자]
     * 회원 정보 수정하기
     * @param request 수정할 회원 정보
     * @return 수정된 사용자 정보
     */
    public Mono<UserInfoResponse> updateUser(RegisterUserRequest request, Long userId, UserRole role) {
        return webClientBuilder.build()
                .post()
                .uri("http://USER-SERVICE/update")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
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

package com.apigateway.filter;

import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient webClient;

    private static final List<String> EXCLUDED = List.of(
            "/user/login",
            "/user/register",
            "/item/list",
            "/user/validation",
            "/item/list"
    );

    private static final List<Pattern> EXCLUDED_PATTERNS = List.of(
            Pattern.compile("^/item/\\d+$"),
            Pattern.compile("^/item/list/\\d+$")
    );

    public AuthenticationFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient.baseUrl("http://USER-SERVICE").build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->
                isExcluded(exchange.getRequest().getURI().getPath()) ?
                        chain.filter(exchange) : validToken(exchange, chain);
    }

    // 특정 url은 인증 제외
    private boolean isExcluded(String requestPath) {
        if (EXCLUDED.stream().anyMatch(requestPath::startsWith)) {
            log.info("Excluded fixed path: {}", requestPath);
            return true;
        }

        if (EXCLUDED_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(requestPath).matches())) {
            log.info("Excluded dynamic path: {}", requestPath);
            return true;
        }

        return false;
    }

    // JWT 검증을 위해 User Server와 통신
    private Mono<Void> validToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Header: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return webClient.get()
                .uri("/validation")
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ValidTokenResponse.class)
                .flatMap(response -> {
                    if(response.isValid()) {
                        // 유저 정보를 담은 새로운 요청으로 교체
                        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                                .header("x-user-id", String.valueOf(response.getId()))
                                .header("x-user-role", String.valueOf(response.getRole()))
                                .build();

                        ServerWebExchange newExchange = exchange.mutate()
                                .request(newRequest)
                                .build();

                        return chain.filter(newExchange);
                    } else {

                        log.info("Invalid Response");

                        return setUnauthorizedResponse(exchange);
                    }
                })
                .onErrorResume(e -> setUnauthorizedResponse(exchange));
    }

    // 401 Unauthorized 응답 설정
    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}

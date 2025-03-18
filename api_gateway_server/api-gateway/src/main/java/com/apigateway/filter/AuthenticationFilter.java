package com.apigateway.filter;

import com.apigateway.aggregation.client.dto.user.response.ValidTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient webClient;
    private static final List<String> EXCLUDED = List.of(
            "/login",
            "/register",
            "/getAllItems"
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
        return EXCLUDED.stream().anyMatch(requestPath::startsWith);
    }

    // JWT 검증을 위해 User Server와 통신
    private Mono<Void> validToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            log.info("Header: {}", header);

            return exchange.getResponse().setComplete();
        }

//        String token = header.substring(7);
        return webClient.get()
                .uri("/validation")
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(ValidTokenResponse.class)
                .flatMap(response -> {
                    if(response.isValid()) {

                        exchange.getAttributes().put("userInfo", response);

                        return chain.filter(exchange);
                    } else {
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

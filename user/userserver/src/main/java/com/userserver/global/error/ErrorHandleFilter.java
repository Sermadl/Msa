package com.userserver.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@Order(-2)
@RequiredArgsConstructor
@Slf4j
public class ErrorHandleFilter implements WebExceptionHandler {

    private final ObjectMapper objectMapper;
    private f ㅇinal MessageSource messageSource;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        Locale locale = exchange.getLocaleContext().getLocale();
        ErrorResponse dto;
        int statusCode;

        if (ex instanceof CustomException customEx) {
            dto = ErrorResponse.of(customEx, messageSource, locale);
            statusCode = customEx.getStatus().value();
            log.error("A problem has occurred in filter: [id={}]", dto.getTrackingId(), customEx);
        } else {
            dto = ErrorResponse.of(CustomException.of(ex), messageSource, locale);
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            log.error("Unexpected exception has occurred in filter: [id={}]", dto.getTrackingId(), ex);
        }

        response.setStatusCode(HttpStatus.valueOf(statusCode));

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            log.error("Error writing error response", e);
            return response.setComplete(); // fallback
        }
    }
}

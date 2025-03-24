package com.userserver.global.error.model.response;

import com.userserver.global.error.model.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@Getter
@Builder
public class ErrorResponse {
    private final String trackingId;
    private final LocalDateTime timestamp;
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse of(CustomException e, MessageSource messageSource, Locale locale) {
        return ErrorResponse.builder()
                .trackingId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .status(e.getStatus().value())
                .code(e.getCode())
                .message(e.getLocalizedMessage(messageSource, locale))
                .build();
    }

    public static ErrorResponse of(WebExchangeBindException e,
                                   MessageSource messageSource, Locale locale) {
        return ErrorResponse.builder()
                .trackingId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .status(e.getStatusCode().value())
                .code(e.getClass().getSimpleName())
                .message(Arrays.toString(e.getDetailMessageArguments(messageSource, locale)))
                .build();
    }
}

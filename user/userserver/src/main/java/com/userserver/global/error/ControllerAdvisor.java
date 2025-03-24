package com.userserver.global.error;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ControllerAdvisor {
    private final MessageSource messageSource;

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(CustomException e, Locale locale) {
        log.error("Exception: {}", e.getMessage(), e);

        ErrorResponse response = ErrorResponse.of(e, messageSource, locale);

        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> webClientException(WebExchangeBindException e, Locale locale) {
        log.error("Validation Exception: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.of(e, messageSource, locale);
        return ResponseEntity.badRequest().body(response);
    }
}

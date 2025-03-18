package com.userserver.user.controller;

import com.userserver.global.auth.jwt.JwtAuthentication;
import com.userserver.user.controller.dto.request.LoginRequest;
import com.userserver.user.controller.dto.request.ValidTokenRequest;
import com.userserver.user.controller.dto.response.LoginResponse;
import com.userserver.user.controller.dto.response.ValidTokenResponse;
import com.userserver.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Validated LoginRequest request
    ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/validation")
    public ResponseEntity<ValidTokenResponse> validate(
            JwtAuthentication auth
    ) {
        return ResponseEntity.ok(
                authService.validToken(auth.getUserId())
        );
    }
}

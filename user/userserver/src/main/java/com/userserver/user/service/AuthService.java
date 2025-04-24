package com.userserver.user.service;

import com.userserver.global.auth.jwt.JwtProvider;
import com.userserver.global.auth.jwt.JwtToken;
import com.userserver.user.controller.dto.request.LoginRequest;
import com.userserver.user.controller.dto.response.LoginResponse;
import com.userserver.user.controller.dto.response.ValidTokenResponse;
import com.userserver.user.exception.IncorrectPassword;
import com.userserver.user.exception.UserNotFoundException;
import com.userserver.user.model.entity.User;
import com.userserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Mono<LoginResponse> login(LoginRequest request) {
        log.info("Login request: {}", request);

        return userRepository.findByEmail(request.getEmail())
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(user -> {
                    if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        JwtToken token = jwtProvider.issue(user);

                        log.info("token provided");

                        return Mono.just(
                                new LoginResponse(
                                        token.getAccessToken(),
                                        token.getRefreshToken(),
                                        user.getUserRole(),
                                        user.getUsername()
                                )
                        );
                    } else {
                        return Mono.error(new IncorrectPassword());
                    }
                });
    }

    public Mono<ValidTokenResponse> validToken(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(user -> Mono.just(
                        new ValidTokenResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getUserRole()
                        )
                ));
    }
}

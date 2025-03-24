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

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            JwtToken token = jwtProvider.issue(user);

            log.info("token: {}", token.getAccessToken());
            return new LoginResponse(token.getAccessToken(), token.getRefreshToken(), user.getUserRole());
        } else {
            throw new IncorrectPassword();
        }
    }

    public ValidTokenResponse validToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new ValidTokenResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUserRole()
        );
    }
}

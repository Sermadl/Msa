package com.userserver.user.service;

import com.userserver.user.controller.dto.request.RegisterUserRequest;
import com.userserver.global.kafka.KafkaProducer;
import com.userserver.user.controller.dto.request.UserUpdateRequest;
import com.userserver.user.controller.dto.response.UserInfoResponse;
import com.userserver.user.exception.UserNotFoundException;
import com.userserver.user.model.entity.User;
import com.userserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

    public Flux<UserInfoResponse> getAll() {
        return userRepository.findAll()
                .map(this::getUserInfo);
    }

    public Mono<UserInfoResponse> getUser(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .map(this::getUserInfo);
    }

    public Mono<UserInfoResponse> register(RegisterUserRequest request) {
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getPhone()
        );

        return userRepository.save(user) // Mono<User>
                .map(this::getUserInfo); // Mono<UserInfoResponse>

    }

    @Transactional
    public Mono<UserInfoResponse> updateUser(Long userId, UserUpdateRequest request) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(user -> {
                    user.update(
                            request.getUsername(),
                            request.getEmail(),
                            request.getPhone()
                    );
                    return userRepository.save(user);
                })
                .map(this::getUserInfo); // DTO 변환
    }


    private UserInfoResponse getUserInfo(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }

}

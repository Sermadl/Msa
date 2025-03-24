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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

    public List<UserInfoResponse> getAll() {
        List<User> users = userRepository.findAll();

        return getAllUserInfo(users);
    }

    public UserInfoResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return getUserInfo(user);
    }

    public UserInfoResponse register(RegisterUserRequest request) {
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getPhone()
        );

        return getUserInfo(
                userRepository.save(user)
        );
    }

    @Transactional
    public UserInfoResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.update(
                request.getUsername(),
                request.getEmail(),
                request.getPhone()
        );

        return getUserInfo(user);
    }

    private UserInfoResponse getUserInfo(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }

    private List<UserInfoResponse> getAllUserInfo(List<User> users) {
        return users.stream().map(
                user -> new UserInfoResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhone()
                )
        ).toList();
    }

}

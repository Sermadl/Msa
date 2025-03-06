package com.userserver.user.service;

import com.userserver.user.controller.dto.request.RegisterUserRequest;
import com.userserver.global.kafka.KafkaProducer;
import com.userserver.user.model.entity.User;
import com.userserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

    public User register(RegisterUserRequest arg) {
        User user = new User(
                arg.getName(),
                passwordEncoder.encode(arg.getPassword()),
                arg.getEmail(),
                arg.getPhone()
        );

        return kafkaProducer.sendDbUpdateMessage(
                userRepository.save(user)
        );
    }
}

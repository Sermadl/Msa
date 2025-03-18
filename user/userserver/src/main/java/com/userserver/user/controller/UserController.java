package com.userserver.user.controller;

import com.userserver.user.controller.dto.request.RegisterUserRequest;
import com.userserver.user.controller.dto.response.UserInfoResponse;
import com.userserver.user.model.entity.User;
import com.userserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAll()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> createUser(@RequestBody RegisterUserRequest request) {
        log.info(request.getUsername());
        log.info(request.getPassword());
        log.info(request.getEmail());
        log.info(request.getPhone());
        return ResponseEntity.ok(userService.register(request));
    }
}

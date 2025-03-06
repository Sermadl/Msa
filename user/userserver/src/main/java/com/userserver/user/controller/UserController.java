package com.userserver.user.controller;

import com.userserver.user.controller.dto.request.RegisterUserRequest;
import com.userserver.user.model.entity.User;
import com.userserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MutationMapping
    public User createUser(@Argument("userRequest") RegisterUserRequest arg) {
        log.info(arg.getName());
        log.info(arg.getPassword());
        log.info(arg.getEmail());
        log.info(arg.getPhone());
        return userService.register(arg);
    }

//    @QueryMapping
//    public List<User> allUsers() {
//        return userRepository.findAll();
//    }
}

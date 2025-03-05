package com.userserver.controller;

import com.userserver.controller.dto.request.RegisterUserRequest;
import com.userserver.model.entity.User;
import com.userserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

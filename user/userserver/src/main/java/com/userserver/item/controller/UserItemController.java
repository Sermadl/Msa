package com.userserver.item.controller;

import com.userserver.item.controller.dto.UserItemResponse;
import com.userserver.item.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserItemController {
    private final UserItemService userItemService;

    @QueryMapping
    public List<UserItemResponse> getUserItems(@Argument Long userId) {
        return userItemService.getUserItems(userId);
    }
}

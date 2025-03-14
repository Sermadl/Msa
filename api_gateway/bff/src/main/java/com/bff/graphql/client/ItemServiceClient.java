package com.bff.graphql.client;

import com.bff.graphql.model.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "item-service")
public interface ItemServiceClient {
    @GetMapping("/items")
    List<Item> getAllItems();

    @GetMapping("/items/user/{userId}")
    List<Item> getItemsByUserId(@PathVariable("userId") String userId);
}

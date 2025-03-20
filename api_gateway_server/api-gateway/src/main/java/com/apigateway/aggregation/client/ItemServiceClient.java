package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.item.request.ItemRegisterRequest;
import com.apigateway.aggregation.client.dto.item.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Flux<ItemResponse> getAllItems() {
        return webClientBuilder.build()
                .get()
                .uri("http://ITEM-SERVICE/list")
                .retrieve()
                .bodyToFlux(ItemResponse.class);
    }

    public Mono<ItemResponse> getItem(Long itemId) {
        return webClientBuilder.build()
                .get()
                .uri("http://ITEM-SERVICE/{itemId}", itemId)
                .retrieve()
                .bodyToMono(ItemResponse.class);
    }

    public Mono<ItemResponse> registerItem(ItemRegisterRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://ITEM-SERVICE/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ItemResponse.class);
    }
}

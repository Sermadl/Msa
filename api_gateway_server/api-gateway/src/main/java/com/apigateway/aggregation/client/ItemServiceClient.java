package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.item.request.ItemRegisterRequest;
import com.apigateway.aggregation.client.dto.item.response.ItemResponse;
import com.apigateway.aggregation.model.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceClient {

    @Value("${item.uri}")
    private String itemUri;
    private final WebClient.Builder webClientBuilder;

    /** [권한 제한 없음]
     * 모든 상품 정보 조회
     * @return 상품 정보 리스트
     */
    public Flux<ItemResponse> getAllItems() {
        return webClientBuilder.build()
                .get()
                .uri(itemUri + "/list")
                .retrieve()
                .bodyToFlux(ItemResponse.class);
    }

    /** [권한 제한 없음]
     * Item Id로 상세 상품 정보 조회
     * @param itemId 조회할 상품 Item Id
     * @return 상품 정보
     */
    public Mono<ItemResponse> getItemById(Long itemId) {
        return webClientBuilder.build()
                .get()
                .uri(itemUri + "/{itemId}", itemId)
                .retrieve()
                .bodyToMono(ItemResponse.class);
    }

    /** [판매자]
     * 상품 등록
     * @param request 상품 등록 Request
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 등록된 상품 정보
     */
    public Mono<ItemResponse> registerItem(ItemRegisterRequest request,
                                           Long userId,
                                           UserRole role) {
        return webClientBuilder.build()
                .post()
                .uri(itemUri + "/register")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ItemResponse.class);
    }
}

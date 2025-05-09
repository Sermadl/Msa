package com.apigateway.aggregation.client;

import com.apigateway.aggregation.client.dto.order.request.PurchaseRequest;
import com.apigateway.aggregation.client.dto.order.response.CartItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderResponse;
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
public class OrderServiceClient {

    @Value("${order.uri}")
    private String orderUri;
    private final WebClient.Builder webClientBuilder;

    /** [관리자]
     * 모든 주문 목록 불러오기
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 목록
     */
    public Flux<OrderResponse> getAllOrders(Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/list")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    /** [사용자]
     * 사용자가 자신이 주문한 건에 대해 조회
     * @param orderId 주문번호
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보
     */
    public Mono<OrderResponse> findUserOrder(Long orderId, Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/{orderId}", orderId)
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    /** [판매자]
     * 판매자가 자신의 상품 주문 건 대해 조회
     * @param orderId 주문번호
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보
     */
    public Mono<OrderResponse> findSellerOrder(Long orderId, Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/seller/{orderId}", orderId)
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    /** [사용자]
     * 사용자가 자신이 주문한 내역을 조회
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    public Flux<OrderResponse> getMyPurchaseList(Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/myList")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    /** [판매자]
     * 판매자가 자신의 판매 내역을 조회
     * @param sellerId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    public Flux<OrderResponse> getPurchaseListForSeller(Long sellerId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/seller/myList")
                .header("x-user-id", String.valueOf(sellerId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    /** [판매자]
     * 등록한 상품 별 주문 내역 조회
     * @param itemId 상품 Id
     * @param sellerId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    public Flux<OrderResponse> getPurchaseListByItem(Long itemId, Long sellerId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/seller/myList/{itemId}", itemId)
                .header("x-user-id", String.valueOf(sellerId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    /** [관리자]
     * 사용자 Id로 주문 내역 조회
     * @param customerId 찾고자 하는 사용자의 Id
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    public Flux<OrderResponse> getUserPurchaseList(Long customerId, Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/list/{customerId}", customerId)
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    /** [사용자]
     * 주문 생성 (상품 구매)
     * @param request 상품 구매 Request
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 생성된 주문 정보
     */
    public Mono<OrderResponse> purchase(PurchaseRequest request, Long userId, UserRole role) {
        return webClientBuilder.build()
                .post()
                .uri(orderUri + "/order")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    public Flux<CartItemResponse> myCart(Long userId, UserRole role) {
        return webClientBuilder.build()
                .get()
                .uri(orderUri + "/cart/my")
                .header("x-user-id", String.valueOf(userId))
                .header("x-user-role", String.valueOf(role))
                .retrieve()
                .bodyToFlux(CartItemResponse.class);
    }
}


package com.orderservice.controller;

import com.orderservice.controller.dto.request.PurchaseRequest;
import com.orderservice.controller.dto.response.OrderResponse;
import com.orderservice.controller.dto.response.OrderSellerResponse;
import com.orderservice.global.util.RoleCheck;
import com.orderservice.global.util.UserRole;
import com.orderservice.model.entity.Orders;
import com.orderservice.service.OrderService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /** [관리자]
     * 모든 주문 목록 불러오기
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 목록
     */
    @GetMapping("/list")
    public Flux<OrderResponse> getAllOrders(
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        log.info("user id({}) accessed to get all orders", userId);
        RoleCheck.isAdmin(role);

        return orderService.getAll();
    }

    /** [사용자]
     * 사용자가 자신이 주문한 건에 대해 조회
     * @param orderId 주문번호
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보
     */
    @GetMapping("/{orderId}")
    public Mono<OrderResponse> findUserOrder(
            @PathVariable("orderId") String orderId,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isUser(role);

        return orderService.getUserOrder(userId, orderId, role);
    }

    /** [판매자]
     * 판매자가 자신의 상품 주문 건 대해 조회
     * @param orderId 주문번호
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보
     */
    @GetMapping("/seller/{orderItemId}")
    public Mono<OrderSellerResponse> findSellerOrder(
            @PathVariable("orderItemId") String orderId,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isSeller(role);

        return orderService.getSellerOrder(userId, orderId, role);
    }

    /** [사용자]
     * 사용자가 자신이 주문한 내역을 조회
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    @GetMapping("/myList")
    public Flux<OrderResponse> getMyPurchaseList(
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isUser(role);

        return orderService.getOrderByCustomerId(userId);
    }

    /** [판매자]
     * 판매자가 자신의 판매 내역을 조회
     * @param sellerId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    @GetMapping("/seller/myList")
    public Flux<OrderSellerResponse> getPurchaseListForSeller(
            @RequestHeader("x-user-id") Long sellerId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isSeller(role);

        return orderService.getOrderBySellerId(sellerId);
    }

    /** [판매자]
     * 등록한 상품 별 주문 내역 조회
     * @param itemId 상품 Id
     * @param sellerId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    @GetMapping("/seller/myList/{itemId}")
    public Flux<OrderSellerResponse> getPurchaseListByItem(
            @PathVariable("itemId") Long itemId,
            @RequestHeader("x-user-id") Long sellerId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isSeller(role);

        return orderService.getOrderBySellerIdAndItemId(sellerId, role, itemId);
    }

    /** [관리자]
     * 사용자 Id로 주문 내역 조회
     * @param customerId 찾고자 하는 사용자의 Id
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 주문 정보 리스트
     */
    @GetMapping("/list/{customerId}")
    public Flux<OrderResponse> getUserPurchaseList(
            @PathVariable("customerId") Long customerId,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        log.info("user id({}) accessed to get customer's({}) orders", userId, customerId);
        RoleCheck.isAdmin(role);

        return orderService.getOrderByCustomerId(customerId);
    }

    /** [사용자]
     * 주문 생성 (상품 구매)
     * @param request 상품 구매 Request
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 생성된 주문 정보
     */
    @PostMapping("/register")
    public Mono<OrderResponse> purchase(
            @RequestBody PurchaseRequest request,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        return orderService.register(request, userId);
    }

}

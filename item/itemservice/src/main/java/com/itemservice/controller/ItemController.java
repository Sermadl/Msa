package com.itemservice.controller;

import com.itemservice.controller.dto.request.ItemRegisterRequest;
import com.itemservice.controller.dto.response.ItemResponse;
import com.itemservice.global.util.RoleCheck;
import com.itemservice.global.util.UserRole;
import com.itemservice.service.ItemService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /** [권한 제한 없음]
     * 모든 상품 정보 조회
     * @return 상품 정보 리스트
     */
    @GetMapping("/list")
    public Flux<ItemResponse> getAllItems() {
        return itemService.getAll();
    }

    /** [권한 제한 없음]
     * Item Id로 상세 상품 정보 조회
     * @param itemId 조회할 상품 Item Id
     * @return 상품 정보
     */
    @GetMapping("/{itemId}")
    public Mono<ItemResponse> getItemById(
            @PathVariable("itemId") Long itemId
    ) {
        return itemService.getItem(itemId);
    }

    /** [판매자]
     * 상품 등록
     * @param request 상품 등록 Request
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 등록된 상품 정보
     */
    @PostMapping("/register")
    public Mono<ItemResponse> registerItem(
            @RequestBody ItemRegisterRequest request,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        RoleCheck.isSeller(role);

        return itemService.register(
                        request,
                        userId
                );
    }
}

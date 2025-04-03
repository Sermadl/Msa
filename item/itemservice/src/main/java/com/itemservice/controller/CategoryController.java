package com.itemservice.controller;

import com.itemservice.controller.dto.request.CategoryRegisterRequest;
import com.itemservice.controller.dto.response.AllCategoryResponse;
import com.itemservice.controller.dto.response.CategoryDetailsResponse;
import com.itemservice.global.util.UserRole;
import com.itemservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /** [권한 제한 없음]
     * 전체 카테고리 조회
     * @return 전체 카테고리 이름 및 깊이 1 세부 카테고리 목록
     */
    @GetMapping
    public Flux<AllCategoryResponse> allLargeCategories() {
        return categoryService.allCategories();
    }

    /** [권한 제한 없음]
     * 개별 카테고리 별 세부 카테고리 조회
     * @param id 개별 카테고리 Id
     * @return 개별 카테고리 별 깊이 1 세부 카테고리 목록
     */
    @GetMapping("/{categoryId}")
    public Mono<CategoryDetailsResponse> categoryDetails(
            @PathVariable("categoryId") Long id
    ) {
        return categoryService.getCategoryDetails(id);
    }

    /** [관리자]
     * 카테고리 등록(추가)
     * @param request 카테고리 등록 Request
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return Void
     */
    @PostMapping("/register")
    public Mono<Void> registerCategory(
            @RequestBody CategoryRegisterRequest request,
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        return categoryService.register(request, userId, role);
    }

}

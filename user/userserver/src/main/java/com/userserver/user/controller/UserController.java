package com.userserver.user.controller;

import com.userserver.global.util.RoleCheck;
import com.userserver.user.controller.dto.request.RegisterUserRequest;
import com.userserver.user.controller.dto.request.UserUpdateRequest;
import com.userserver.user.controller.dto.response.UserInfoResponse;
import com.userserver.user.model.entity.UserRole;
import com.userserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** [관리자]
     * 모든 사용자 정보 가져오기
     * @param userId 로그인 된 사용자 Id
     * @param role 로그인 된 사용자 Role
     * @return 모든 사용자 정보 리스트
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserInfoResponse>> getAllUsers(
            @RequestHeader("x-user-id") Long userId,
            @RequestHeader("x-user-role") UserRole role
    ) {
        log.info("user id({}) accessed to find all users", userId);

        RoleCheck.isAdmin(role);

        return ResponseEntity.ok(
                userService.getAll()
        );
    }

    /** [관리자]
     * User ID로 사용자 찾기
     * @param targetId 찾을 사용자의 User Id
     * @param userId 로그인 된 사용자 Id
     * @param role 로그인 된 사용자 Role
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable("userId") Long targetId,
                                                        @RequestHeader("x-user-id") Long userId,
                                                        @RequestHeader("x-user-role") UserRole role) {
        log.info("user id({}) accessed to find user", userId);
        RoleCheck.isAdmin(role);

        return ResponseEntity.ok(userService.getUser(targetId));
    }

    /** [사용자]
     * 로그인 된 사용자 정보 불러오기
     * @param userId 로그인 된 사용자 User Id
     * @param role 로그인 된 사용자 Role
     * @return 로그인 된 사용자 정보
     */
    @GetMapping("/my")
    public ResponseEntity<UserInfoResponse> getMyInfo(@RequestHeader("x-user-id") Long userId,
                                                      @RequestHeader("x-user-role") UserRole role) {
        log.info("user id({}) accessed to find user", userId);
        RoleCheck.isUser(role);

        return ResponseEntity.ok(userService.getUser(userId));
    }

    /** [권한 제한 없음]
     * 회원가입하기
     * @param request 회원가입 정보
     * @return 등록된 사용자 정보
     */
    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> createUser(@RequestBody RegisterUserRequest request) {
        log.info(request.getUsername());
        log.info(request.getPassword());
        log.info(request.getEmail());
        log.info(request.getPhone());
        return ResponseEntity.ok(userService.register(request));
    }

    /** [사용자]
     * 회원 정보 수정하기
     * @param request 수정할 회원 정보
     * @return 수정된 사용자 정보
     */
    @PatchMapping("/update")
    public ResponseEntity<UserInfoResponse> updateUser(@RequestBody UserUpdateRequest request,
                                                        @RequestHeader("x-user-id") Long userId,
                                                        @RequestHeader("x-user-role") UserRole role) {
        log.info("user Id({}) accessed to update user", userId);
        RoleCheck.isUser(role);

        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}

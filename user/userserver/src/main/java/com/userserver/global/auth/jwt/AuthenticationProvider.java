package com.userserver.global.auth.jwt;


import com.userserver.user.model.entity.UserRole;

public interface AuthenticationProvider {
    String createAccessToken(String userId, UserRole userRole);
    String createRefreshToken(String userId, UserRole userRole);

}

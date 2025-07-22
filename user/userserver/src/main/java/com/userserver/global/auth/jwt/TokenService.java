package com.userserver.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public Mono<Boolean> storeRefreshTokenJti(String userId, String jti) {
        return redisTemplate.opsForValue().set(userId, jti);
    }

    public Mono<Boolean> resetRefreshTokenJti(String userId, String token) {
        return redisTemplate.opsForValue().setIfPresent(userId, token);
    }

    public Mono<Boolean> isRefreshTokenValid(String userId, String jti) {
        return redisTemplate.opsForValue()
                .get(userId)
                .map(jti::equals)
                .defaultIfEmpty(false); // 값이 없으면 false 반환
    }
}

package com.apigateway.aggregation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/actuator")
public class CustomActuatorController {

    private final AtomicBoolean isReady = new AtomicBoolean(true);
    private final AtomicBoolean isLive = new AtomicBoolean(true);

    @GetMapping("/health")
    public Mono<ResponseEntity<Map<String, Object>>> health() {
        Map<String, Object> health = new HashMap<>();
        if (isLive.get()) {
            health.put("status", "UP");
            return Mono.just(ResponseEntity.ok(health));
        } else {
            health.put("status", "DOWN");
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(health)); // 400
        }
    }

    @GetMapping("/ready")
    public Mono<ResponseEntity<Map<String, Object>>> readiness() {
        Map<String, Object> status = new HashMap<>();
        if (isReady.get()) {
            status.put("status", "READY");
            return Mono.just(ResponseEntity.ok(status));
        } else {
            status.put("status", "NOT READY");
            return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(status)); // 503
        }
    }

    @PostMapping("/health/{flag}")
    public Mono<ResponseEntity<Map<String, Object>>> setReadiness(@PathVariable boolean flag) {
        isLive.set(flag);
        return health();
    }

    @PostMapping("/ready/{flag}")
    public Mono<ResponseEntity<Map<String, Object>>> setLiveness(@PathVariable boolean flag) {
        isReady.set(flag);
        return readiness();
    }
}

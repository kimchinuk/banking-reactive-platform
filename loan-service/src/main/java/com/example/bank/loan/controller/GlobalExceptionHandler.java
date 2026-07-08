package com.example.bank.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    Mono<ResponseEntity<Map<String, String>>> badRequest(IllegalArgumentException ex) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of("error", "VALIDATION_ERROR", "message", ex.getMessage())));
    }

    @ExceptionHandler(IllegalStateException.class)
    Mono<ResponseEntity<Map<String, String>>> unavailable(IllegalStateException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "DEPENDENCY_UNAVAILABLE", "message", ex.getMessage())));
    }
}

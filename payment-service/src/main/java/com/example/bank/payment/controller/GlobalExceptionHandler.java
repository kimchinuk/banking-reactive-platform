package com.example.bank.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Architecture/Tech: Centralized WebFlux exception mapping for payment APIs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    Mono<ResponseEntity<Map<String, String>>> badRequest(IllegalArgumentException ex) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of(
                "error", "VALIDATION_ERROR",
                "message", ex.getMessage()
        )));
    }
}

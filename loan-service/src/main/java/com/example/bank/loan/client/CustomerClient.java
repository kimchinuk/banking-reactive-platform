package com.example.bank.loan.client;

import com.example.bank.common.dto.CustomerResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Architecture/Tech: Reactive WebClient with Resilience4j CircuitBreaker + Retry + Bulkhead + RateLimiter.
 */
@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final WebClient customerWebClient;

    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @Bulkhead(name = "customerService")
    @RateLimiter(name = "customerService")
    public Mono<CustomerResponse> fetchCustomer(String customerId, String correlationId) {
        return customerWebClient.get()
                .uri("/customers/{customerId}", customerId)
                .header("X-Correlation-Id", correlationId == null ? "" : correlationId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .onErrorMap(ex -> new IllegalStateException("Customer validation temporarily unavailable", ex));
    }
}

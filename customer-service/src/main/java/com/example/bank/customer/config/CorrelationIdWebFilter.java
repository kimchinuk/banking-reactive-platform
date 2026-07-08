package com.example.bank.customer.config;

import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Architecture/Tech: Centralized logging via correlation IDs in a reactive WebFlux filter.
 */
@Component
public class CorrelationIdWebFilter implements WebFilter {

    public static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
        String resolved = (correlationId == null || correlationId.isBlank()) ? UUID.randomUUID().toString() : correlationId;

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(CORRELATION_ID, resolved)
                .build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        mutatedExchange.getResponse().getHeaders().set(CORRELATION_ID, resolved);

        return chain.filter(mutatedExchange)
                .doFirst(() -> MDC.put("correlationId", resolved))
                .doFinally(signalType -> MDC.remove("correlationId"));
    }
}

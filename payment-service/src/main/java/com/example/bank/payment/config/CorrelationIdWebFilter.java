package com.example.bank.payment.config;

import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Architecture/Tech: Centralized logging filter propagating correlation ID through reactive request processing.
 */
@Component
public class CorrelationIdWebFilter implements WebFilter {

    public static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String incoming = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
        String correlationId = (incoming == null || incoming.isBlank()) ? UUID.randomUUID().toString() : incoming;

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(CORRELATION_ID, correlationId)
                .build();
        ServerWebExchange updatedExchange = exchange.mutate().request(request).build();
        updatedExchange.getResponse().getHeaders().set(CORRELATION_ID, correlationId);

        return chain.filter(updatedExchange)
                .doFirst(() -> MDC.put("correlationId", correlationId))
                .doFinally(signalType -> MDC.remove("correlationId"));
    }
}

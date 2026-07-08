package com.example.bank.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Architecture/Tech: Spring Cloud Gateway global filter for centralized correlation-ID propagation.
 */
@Component
public class CorrelationIdGatewayFilter implements GlobalFilter, Ordered {

    public static final String CORRELATION_ID = "X-Correlation-Id";
    private static final Logger log = LoggerFactory.getLogger(CorrelationIdGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String existing = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
        String correlationId = (existing == null || existing.isBlank()) ? UUID.randomUUID().toString() : existing;

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(CORRELATION_ID, correlationId)
                .build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(request).build();
        mutatedExchange.getResponse().getHeaders().set(CORRELATION_ID, correlationId);

        return chain.filter(mutatedExchange)
                .doFirst(() -> {
                    MDC.put("correlationId", correlationId);
                    log.debug("Gateway request path={} correlationId={}", request.getPath(), correlationId);
                })
                .doFinally(signalType -> MDC.remove("correlationId"));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

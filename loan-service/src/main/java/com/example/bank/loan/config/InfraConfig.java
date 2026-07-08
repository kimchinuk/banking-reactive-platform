package com.example.bank.loan.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Aggregated infrastructure configuration:
 * - WebClient bean for downstream HTTP
 * - Kafka topic creation for demo convenience
 * - Resilience4j defaults for reactive circuit breaker
 */
@Configuration
public class InfraConfig {

    @Bean
    WebClient customerWebClient(WebClient.Builder builder, @Value("${clients.customer.base-url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    NewTopic loanEventsTopic() {
        return TopicBuilder.name("loan-events").partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic loanDecisionsTopic() {
        return TopicBuilder.name("loan-decisions").partitions(3).replicas(1).build();
    }

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .failureRateThreshold(50)
                        .minimumNumberOfCalls(5)
                        .slidingWindowSize(10)
                        .waitDurationInOpenState(Duration.ofSeconds(10))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
                .build());
    }
}

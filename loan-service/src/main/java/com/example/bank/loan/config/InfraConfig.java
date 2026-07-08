package com.example.bank.loan.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Aggregated infrastructure configuration:
 * - WebClient bean for downstream HTTP
 * - Kafka topic creation for demo convenience
 * - Resilience4j policies configured in application.yml
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
}

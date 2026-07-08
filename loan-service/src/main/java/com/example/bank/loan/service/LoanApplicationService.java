package com.example.bank.loan.service;

import com.example.bank.common.dto.CustomerResponse;
import com.example.bank.common.dto.LoanApplicationRequest;
import com.example.bank.common.dto.LoanApplicationResponse;
import com.example.bank.common.event.LoanEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Background on the design:
 *
 * - Customer lookup is synchronous from a business perspective, but technically non-blocking via WebClient.
 * - Circuit breaker keeps repeated failures from consuming more resources.
 * - Kafka allows the API to acknowledge quickly while risk processing happens asynchronously.
 */
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final WebClient customerWebClient;
    private final ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Mono<LoanApplicationResponse> apply(LoanApplicationRequest request) {
        String applicationId = UUID.randomUUID().toString();

        Mono<CustomerResponse> customerMono = customerWebClient.get()
                .uri("/customers/{customerId}", request.customerId())
                .retrieve()
                .bodyToMono(CustomerResponse.class);

        return circuitBreakerFactory.create("customerService")
                .run(customerMono,
                        ex -> Mono.error(new IllegalStateException("Customer validation temporarily unavailable", ex)))
                .flatMap(customer -> validate(customer, request))
                .then(Mono.create(sink -> kafkaTemplate.send(
                                "loan-events",
                                applicationId,
                                new LoanEvent(applicationId, request.customerId(), request.amount(), request.termMonths(), request.purpose()))
                        .whenComplete((result, ex) -> {
                            if (ex != null) sink.error(ex); else sink.success();
                        })))
                .thenReturn(new LoanApplicationResponse(applicationId, "ACCEPTED", "Submitted for async risk processing"));
    }

    private Mono<CustomerResponse> validate(CustomerResponse customer, LoanApplicationRequest request) {
        if (Boolean.FALSE.equals(customer.active())) {
            return Mono.error(new IllegalArgumentException("Customer is inactive"));
        }
        if (customer.creditScore() < 500) {
            return Mono.error(new IllegalArgumentException("Credit score below threshold"));
        }
        if (request.amount() > 100000) {
            return Mono.error(new IllegalArgumentException("Amount exceeds demo maximum"));
        }
        return Mono.just(customer);
    }
}

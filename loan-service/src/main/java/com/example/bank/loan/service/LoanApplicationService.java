package com.example.bank.loan.service;

import com.example.bank.common.dto.CustomerResponse;
import com.example.bank.common.dto.LoanApplicationRequest;
import com.example.bank.common.dto.LoanApplicationResponse;
import com.example.bank.common.event.LoanEvent;
import com.example.bank.loan.client.CustomerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Architecture/Tech: WebFlux orchestration + Resilience4j-enabled reactive client + Kafka async boundary.
 */
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final CustomerClient customerClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Mono<LoanApplicationResponse> apply(LoanApplicationRequest request, String correlationId) {
        String applicationId = UUID.randomUUID().toString();

        return customerClient.fetchCustomer(request.customerId(), correlationId)
                .flatMap(customer -> validate(customer, request))
                .then(Mono.create(sink -> kafkaTemplate.send(
                                "loan-events",
                                applicationId,
                                new LoanEvent(applicationId, request.customerId(), request.amount(), request.termMonths(), request.purpose(), correlationId))
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

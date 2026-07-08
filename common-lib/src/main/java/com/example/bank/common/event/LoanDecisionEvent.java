package com.example.bank.common.event;

/**
 * Architecture/Tech: Kafka decision event with correlation context for centralized observability.
 */
public record LoanDecisionEvent(
        String applicationId,
        String customerId,
        String decision,
        String reason,
        String correlationId
) {}

package com.example.bank.common.event;

/**
 * Architecture/Tech: Kafka event contract carrying reactive workflow context and correlation ID.
 */
public record LoanEvent(
        String applicationId,
        String customerId,
        Double amount,
        Integer termMonths,
        String purpose,
        String correlationId
) {}

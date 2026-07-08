package com.example.bank.common.event;

/**
 * Event published by Loan Service to start asynchronous risk processing.
 */
public record LoanEvent(String applicationId, String customerId, Double amount, Integer termMonths, String purpose) {}

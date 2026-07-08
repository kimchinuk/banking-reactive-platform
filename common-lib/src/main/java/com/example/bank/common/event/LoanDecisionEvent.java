package com.example.bank.common.event;

/**
 * Event published by Risk Service after decisioning.
 */
public record LoanDecisionEvent(String applicationId, String customerId, String decision, String reason) {}

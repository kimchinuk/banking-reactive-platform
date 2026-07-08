package com.example.bank.common.dto;

/**
 * Immediate API response returned after the synchronous part of processing completes.
 */
public record LoanApplicationResponse(String applicationId, String status, String message) {}

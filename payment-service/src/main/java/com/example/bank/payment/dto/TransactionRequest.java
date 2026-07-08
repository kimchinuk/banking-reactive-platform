package com.example.bank.payment.dto;

import com.example.bank.payment.domain.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Architecture/Tech: DTO with Jakarta Bean Validation for robust API input contracts.
 */
public record TransactionRequest(
        @NotBlank String transactionId,
        @NotBlank String accountId,
        @NotNull @DecimalMin("0.01") Double amount,
        @NotNull TransactionType type
) {
}

package com.example.bank.common.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Immutable request payload for loan submission.
 *
 * Why a record?
 * - compact and expressive
 * - naturally immutable, which is helpful when code becomes asynchronous/reactive
 */
public record LoanApplicationRequest(
        @NotBlank String customerId,
        @NotNull @DecimalMin("1.0") Double amount,
        @NotNull Integer termMonths,
        @NotBlank String purpose
) {}

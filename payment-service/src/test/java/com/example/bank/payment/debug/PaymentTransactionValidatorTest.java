package com.example.bank.payment.debug;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Architecture/Tech: Regression test for debugging fix in payment transaction validation.
 */
class PaymentTransactionValidatorTest {

    private final PaymentTransactionValidator validator = new PaymentTransactionValidator();

    @Test
    void shouldAcceptPositiveAmountAndRejectZeroOrNegative() {
        assertTrue(validator.isValid(new Transaction("T1", "A1", new BigDecimal("1.00"), TransactionType.CREDIT, Instant.now())));
        assertFalse(validator.isValid(new Transaction("T2", "A1", new BigDecimal("0.00"), TransactionType.CREDIT, Instant.now())));
        assertFalse(validator.isValid(new Transaction("T3", "A1", new BigDecimal("-1.00"), TransactionType.DEBIT, Instant.now())));
    }
}

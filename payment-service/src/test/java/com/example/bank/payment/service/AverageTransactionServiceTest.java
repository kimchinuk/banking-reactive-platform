package com.example.bank.payment.service;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Architecture/Tech: Unit test validating average-amount aggregation per account.
 */
class AverageTransactionServiceTest {

    private final AverageTransactionService service = new AverageTransactionService();

    @Test
    void shouldCalculateAveragePerAccount() {
        List<Transaction> transactions = List.of(
                new Transaction("T1", "A1", BigDecimal.valueOf(10), TransactionType.CREDIT, Instant.now()),
                new Transaction("T2", "A1", BigDecimal.valueOf(20), TransactionType.DEBIT, Instant.now()),
                new Transaction("T3", "A2", BigDecimal.valueOf(9), TransactionType.CREDIT, Instant.now())
        );

        Map<String, BigDecimal> result = service.calculateAveragePerAccount(transactions);

        assertEquals(new BigDecimal("15.00"), result.get("A1"));
        assertEquals(new BigDecimal("9.00"), result.get("A2"));
    }
}

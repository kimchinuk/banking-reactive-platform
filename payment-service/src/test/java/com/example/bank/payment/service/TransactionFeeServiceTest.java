package com.example.bank.payment.service;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import com.example.bank.payment.fee.CreditFeePolicy;
import com.example.bank.payment.fee.DebitFeePolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Architecture/Tech: Unit test validating strategy-based fee aggregation.
 */
class TransactionFeeServiceTest {

    private final TransactionFeeService service = new TransactionFeeService(List.of(new CreditFeePolicy(), new DebitFeePolicy()));

    @Test
    void shouldCalculateFeesPerAccount() {
        List<Transaction> transactions = List.of(
                new Transaction("T1", "A1", BigDecimal.valueOf(100), TransactionType.CREDIT, Instant.now()),
                new Transaction("T2", "A1", BigDecimal.valueOf(80), TransactionType.DEBIT, Instant.now()),
                new Transaction("T3", "A2", BigDecimal.valueOf(50), TransactionType.DEBIT, Instant.now())
        );

        Map<String, Integer> result = service.calculateTotalFeePerAccount(transactions);

        assertEquals(3, result.get("A1"));
        assertEquals(2, result.get("A2"));
    }
}

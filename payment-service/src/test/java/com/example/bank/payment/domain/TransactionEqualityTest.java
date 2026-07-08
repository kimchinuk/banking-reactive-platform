package com.example.bank.payment.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Architecture/Tech: Unit test validating equals/hashCode contract behavior in hash-based collections.
 */
class TransactionEqualityTest {

    @Test
    void shouldTreatSameIdentityTransactionAsSingleHashSetEntry() {
        Transaction first = new Transaction("T1", "A1", new BigDecimal("10.00"), TransactionType.CREDIT, Instant.now());
        Transaction duplicateIdentity = new Transaction("T1", "A1", new BigDecimal("10.00"), TransactionType.CREDIT, Instant.now());

        Set<Transaction> set = new HashSet<>();
        set.add(first);
        set.add(duplicateIdentity);

        assertEquals(1, set.size());
    }
}

package com.example.bank.payment.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Architecture/Tech: Domain model with explicit equals/hashCode contract for reliable map/set behavior.
 */
public final class Transaction {
    private final String transactionId;
    private final String accountId;
    private final BigDecimal amount;
    private final TransactionType type;
    private final Instant createdAt;

    public Transaction(String transactionId, String accountId, BigDecimal amount, TransactionType type, Instant createdAt) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId);
    }
}

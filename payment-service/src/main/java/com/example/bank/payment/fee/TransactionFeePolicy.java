package com.example.bank.payment.fee;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;

/**
 * Architecture/Tech: Strategy pattern contract to remove type-based if/else fee logic.
 */
public interface TransactionFeePolicy {
    TransactionType supportedType();
    int feeFor(Transaction transaction);
}

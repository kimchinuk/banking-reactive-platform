package com.example.bank.payment.fee;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import org.springframework.stereotype.Component;

/**
 * Architecture/Tech: Fee strategy for DEBIT transactions.
 */
@Component
public class DebitFeePolicy implements TransactionFeePolicy {
    @Override
    public TransactionType supportedType() {
        return TransactionType.DEBIT;
    }

    @Override
    public int feeFor(Transaction transaction) {
        return 2;
    }
}

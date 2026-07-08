package com.example.bank.payment.fee;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import org.springframework.stereotype.Component;

/**
 * Architecture/Tech: Fee strategy for CREDIT transactions.
 */
@Component
public class CreditFeePolicy implements TransactionFeePolicy {
    @Override
    public TransactionType supportedType() {
        return TransactionType.CREDIT;
    }

    @Override
    public int feeFor(Transaction transaction) {
        return 1;
    }
}

package com.example.bank.payment.debug;

import com.example.bank.payment.domain.Transaction;
import org.springframework.stereotype.Component;

/**
 * Architecture/Tech: Processor class in the three-class debugging exercise.
 */
@Component
public class PaymentTransactionProcessor {

    private final PaymentTransactionValidator validator;
    private final TransactionLedger ledger;

    public PaymentTransactionProcessor(PaymentTransactionValidator validator, TransactionLedger ledger) {
        this.validator = validator;
        this.ledger = ledger;
    }

    public void process(Transaction transaction) {
        if (!validator.isValid(transaction)) {
            throw new IllegalArgumentException("Invalid transaction amount: " + transaction.getAmount());
        }
        ledger.add(transaction);
    }
}

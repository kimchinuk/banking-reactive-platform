package com.example.bank.payment.debug;

import com.example.bank.payment.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Architecture/Tech: In-memory ledger used in debugging scenario to store validated payment transactions.
 */
@Component
public class TransactionLedger {

    private final List<Transaction> transactions = new ArrayList<>();

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> all() {
        return Collections.unmodifiableList(transactions);
    }
}

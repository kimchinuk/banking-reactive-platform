package com.example.bank.payment.service;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.domain.TransactionType;
import com.example.bank.payment.fee.TransactionFeePolicy;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Architecture/Tech: DI-driven fee calculation service using strategy implementations.
 */
@Service
public class TransactionFeeService {

    private final Map<TransactionType, TransactionFeePolicy> feePolicies;

    public TransactionFeeService(List<TransactionFeePolicy> policies) {
        Map<TransactionType, TransactionFeePolicy> map = new EnumMap<>(TransactionType.class);
        for (TransactionFeePolicy policy : policies) {
            map.put(policy.supportedType(), policy);
        }
        this.feePolicies = map;
    }

    public Map<String, Integer> calculateTotalFeePerAccount(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getAccountId,
                        Collectors.summingInt(tx -> resolvePolicy(tx.getType()).feeFor(tx))
                ));
    }

    private TransactionFeePolicy resolvePolicy(TransactionType type) {
        TransactionFeePolicy policy = feePolicies.get(type);
        if (policy == null) {
            throw new IllegalArgumentException("No fee policy registered for transaction type: " + type);
        }
        return policy;
    }
}

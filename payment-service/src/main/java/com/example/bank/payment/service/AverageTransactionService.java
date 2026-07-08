package com.example.bank.payment.service;

import com.example.bank.payment.domain.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Architecture/Tech: Stateless business service computing average transaction amount per account.
 */
@Service
public class AverageTransactionService {

    public Map<String, BigDecimal> calculateAveragePerAccount(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getAccountId,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.collectingAndThen(Collectors.toList(), amounts -> {
                                    BigDecimal total = amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                                    return total.divide(BigDecimal.valueOf(amounts.size()), 2, RoundingMode.HALF_UP);
                                })
                        )
                ));
    }
}

package com.example.bank.payment.mapper;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.dto.TransactionRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Architecture/Tech: Stateless mapping utility between API DTOs and payment domain models.
 */
public final class TransactionMapper {

    private TransactionMapper() {
    }

    public static Transaction toDomain(TransactionRequest request) {
        return new Transaction(
                request.transactionId(),
                request.accountId(),
                BigDecimal.valueOf(request.amount()),
                request.type(),
                Instant.now()
        );
    }

    public static List<Transaction> toDomainList(List<TransactionRequest> requests) {
        return requests.stream().map(TransactionMapper::toDomain).toList();
    }
}

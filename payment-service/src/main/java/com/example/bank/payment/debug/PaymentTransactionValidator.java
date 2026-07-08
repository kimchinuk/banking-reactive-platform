package com.example.bank.payment.debug;

import com.example.bank.payment.domain.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Architecture/Tech: Debugging exercise class (root-cause fix).
 * Root cause fixed: BigDecimal must be compared with compareTo, not equals/== semantics for numeric checks.
 */
@Component
public class PaymentTransactionValidator {

    public boolean isValid(Transaction transaction) {
        return transaction.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}

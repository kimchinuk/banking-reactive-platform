package com.example.bank.payment.controller;

import com.example.bank.payment.domain.Transaction;
import com.example.bank.payment.dto.TheoryAnswer;
import com.example.bank.payment.dto.TransactionRequest;
import com.example.bank.payment.debug.PaymentTransactionProcessor;
import com.example.bank.payment.mapper.TransactionMapper;
import com.example.bank.payment.service.AverageTransactionService;
import com.example.bank.payment.service.PaymentTheoryService;
import com.example.bank.payment.service.TransactionFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Architecture/Tech: Reactive WebFlux controller for payment analytics and interview-style design/coding content.
 */
@Validated
@RestController
@RequestMapping("/payments")
@Tag(name = "Payments", description = "Payment transaction scenario APIs")
public class PaymentController {

    private final AverageTransactionService averageTransactionService;
    private final TransactionFeeService transactionFeeService;
    private final PaymentTheoryService paymentTheoryService;
    private final PaymentTransactionProcessor paymentTransactionProcessor;

    public PaymentController(
            AverageTransactionService averageTransactionService,
            TransactionFeeService transactionFeeService,
            PaymentTheoryService paymentTheoryService,
            PaymentTransactionProcessor paymentTransactionProcessor
    ) {
        this.averageTransactionService = averageTransactionService;
        this.transactionFeeService = transactionFeeService;
        this.paymentTheoryService = paymentTheoryService;
        this.paymentTransactionProcessor = paymentTransactionProcessor;
    }

    @PostMapping("/analytics/average")
    @Operation(summary = "Calculate average transaction amount per account")
    public Mono<Map<String, BigDecimal>> averageByAccount(@Valid @RequestBody List<@Valid TransactionRequest> requests) {
        List<Transaction> transactions = TransactionMapper.toDomainList(requests);
        return Mono.just(averageTransactionService.calculateAveragePerAccount(transactions));
    }

    @PostMapping("/fees/total")
    @Operation(summary = "Calculate total transaction fee per account (Credit=$1, Debit=$2)")
    public Mono<Map<String, Integer>> totalFeesByAccount(@Valid @RequestBody List<@Valid TransactionRequest> requests) {
        List<Transaction> transactions = TransactionMapper.toDomainList(requests);
        return Mono.just(transactionFeeService.calculateTotalFeePerAccount(transactions));
    }

    @GetMapping("/theory")
    @Operation(summary = "Get theoretical answers for Java/design interview questions")
    public Mono<List<TheoryAnswer>> theoryAnswers() {
        return Mono.just(paymentTheoryService.answers());
    }

    @PostMapping("/debug/process")
    @Operation(summary = "Run the debugging exercise processor and persist a validated transaction")
    public Mono<Map<String, String>> processDebugTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = TransactionMapper.toDomain(request);
        paymentTransactionProcessor.process(transaction);
        return Mono.just(Map.of("status", "PROCESSED", "transactionId", request.transactionId()));
    }
}

package com.example.bank.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Loan Service accepts requests synchronously, validates the customer through WebClient,
 * and then publishes an event to Kafka so downstream decisioning becomes asynchronous.
 */
@SpringBootApplication
public class LoanServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }
}

package com.example.bank.common.dto;

/**
 * Customer profile DTO exchanged between services.
 */
public record CustomerResponse(String customerId, String fullName, Integer creditScore, Boolean active) {}

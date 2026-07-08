package com.example.bank.loan.controller;

import com.example.bank.common.dto.LoanApplicationRequest;
import com.example.bank.common.dto.LoanApplicationResponse;
import com.example.bank.loan.service.LoanApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Architecture/Tech: Spring WebFlux + OpenAPI controller layer.
 * Delegates orchestration and resilience to service components.
 */
@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Reactive loan submission API")
public class LoanController {

    private final LoanApplicationService service;

    @PostMapping("/apply")
    @Operation(summary = "Submit a loan application for async risk processing")
    public Mono<ResponseEntity<LoanApplicationResponse>> apply(
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @Valid @RequestBody LoanApplicationRequest request
    ) {
        return service.apply(request, correlationId)
                .map(res -> ResponseEntity.status(HttpStatus.ACCEPTED).body(res));
    }
}

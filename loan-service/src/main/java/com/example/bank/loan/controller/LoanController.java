package com.example.bank.loan.controller;

import com.example.bank.common.dto.LoanApplicationRequest;
import com.example.bank.common.dto.LoanApplicationResponse;
import com.example.bank.loan.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Thin reactive controller. It delegates orchestration to the service layer,
 * which is a common clean architecture practice in Spring microservices.
 */
@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanApplicationService service;

    @PostMapping("/apply")
    public Mono<ResponseEntity<LoanApplicationResponse>> apply(@Valid @RequestBody LoanApplicationRequest request) {
        return service.apply(request)
                .map(res -> ResponseEntity.status(HttpStatus.ACCEPTED).body(res));
    }
}

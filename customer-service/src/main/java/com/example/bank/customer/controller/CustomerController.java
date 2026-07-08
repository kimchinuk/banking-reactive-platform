package com.example.bank.customer.controller;

import com.example.bank.common.dto.CustomerResponse;
import com.example.bank.customer.service.CustomerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Architecture/Tech: Spring WebFlux + Reactive R2DBC + OpenAPI.
 * This controller keeps an asynchronous contract while delegating persistence to a reactive service layer.
 */
@RestController
@RequestMapping("/customers")
@Tag(name = "Customers", description = "Reactive customer profile APIs backed by PostgreSQL + R2DBC")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    @GetMapping("/{customerId}")
    @Operation(summary = "Fetch a customer profile by customerId")
    public Mono<ResponseEntity<CustomerResponse>> get(@PathVariable String customerId) {
        return customerQueryService.findById(customerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

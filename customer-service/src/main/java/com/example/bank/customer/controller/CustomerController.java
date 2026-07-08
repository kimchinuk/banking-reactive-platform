package com.example.bank.customer.controller;

import com.example.bank.common.dto.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Simple reactive controller that simulates customer master-data lookup.
 *
 * Returning Mono allows non-blocking completion even if the backing store later becomes remote/database-based.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Map<String, CustomerResponse> DATA = Map.of(
            "CUST-1001", new CustomerResponse("CUST-1001", "Alex Johnson", 760, true),
            "CUST-1002", new CustomerResponse("CUST-1002", "Priya Shah", 640, true),
            "CUST-1003", new CustomerResponse("CUST-1003", "Chris Walker", 480, true),
            "CUST-1004", new CustomerResponse("CUST-1004", "Taylor Brown", 700, false)
    );

    @GetMapping("/{customerId}")
    public Mono<ResponseEntity<CustomerResponse>> get(@PathVariable String customerId) {
        return Mono.justOrEmpty(DATA.get(customerId))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

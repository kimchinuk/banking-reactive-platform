package com.example.bank.customer.service;

import com.example.bank.common.dto.CustomerResponse;
import com.example.bank.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Architecture/Tech: Reactive service layer on top of R2DBC repository.
 */
@Service
@RequiredArgsConstructor
public class CustomerQueryService {

    private final CustomerRepository customerRepository;

    public Mono<CustomerResponse> findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(entity -> new CustomerResponse(
                        entity.customerId(),
                        entity.fullName(),
                        entity.creditScore(),
                        entity.active()
                ));
    }
}

package com.example.bank.customer.repository;

import com.example.bank.customer.model.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Architecture/Tech: Spring Data R2DBC repository abstraction for PostgreSQL.
 */
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, String> {
}

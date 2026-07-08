package com.example.bank.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Architecture/Tech: Spring Data R2DBC + PostgreSQL.
 * Reactive aggregate representing persisted customer profile data.
 */
@Table("customers")
public record CustomerEntity(
        @Id @Column("customer_id") String customerId,
        @Column("full_name") String fullName,
        @Column("credit_score") Integer creditScore,
        @Column("active") Boolean active
) {
}

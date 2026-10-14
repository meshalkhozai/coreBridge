package com.corebridge.api.repository;

import com.corebridge.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByExternalId(String externalId);
    boolean existsByEmail(String email);
}

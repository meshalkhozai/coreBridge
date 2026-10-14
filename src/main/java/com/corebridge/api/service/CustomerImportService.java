package com.corebridge.api.service;

import com.corebridge.api.dto.ExternalCustomerDto;
import com.corebridge.api.entity.Customer;
import com.corebridge.api.entity.CustomerType;
import com.corebridge.api.exception.BadRequestException;
import com.corebridge.api.integration.ExternalProviderClient;
import com.corebridge.api.repository.CustomerRepository;
import com.corebridge.api.util.ExternalIdNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class CustomerImportService {

    private final ExternalProviderClient externalProviderClient;
    private final CustomerRepository customerRepository;
    private final ExternalIdNormalizer externalIdNormalizer;

    public CustomerImportService(ExternalProviderClient externalProviderClient,
                                 CustomerRepository customerRepository,
                                 ExternalIdNormalizer externalIdNormalizer) {
        this.externalProviderClient = externalProviderClient;
        this.customerRepository = customerRepository;
        this.externalIdNormalizer = externalIdNormalizer;
    }

    public int importCustomers() {
        List<ExternalCustomerDto> payload = externalProviderClient.fetchCustomers();
        int count = 0;
        for (ExternalCustomerDto dto : payload) {
            upsert(dto);
            count++;
        }
        return count;
    }

    private void upsert(ExternalCustomerDto dto) {
        CustomerType type = parseType(dto.getCustomerType());
        String fullName = trimOrNull(dto.getName());
        String businessName = trimOrNull(dto.getBusinessName());
        String email = trimOrNull(dto.getEmail());
        String phone = trimOrNull(dto.getPhone());
        String externalId = externalIdNormalizer.normalize(dto.getExternalId());

        if (type == CustomerType.INDIVIDUAL && fullName == null) {
            throw new BadRequestException("External customer missing name");
        }
        if (type == CustomerType.BUSINESS && businessName == null) {
            throw new BadRequestException("External customer missing business name");
        }

        Customer existing = externalId == null ? null
                : customerRepository.findByExternalId(externalId).orElse(null);

        if (existing == null) {
            Customer customer = Customer.builder()
                    .customerType(type)
                    .fullName(fullName)
                    .businessName(businessName)
                    .email(email)
                    .phone(phone)
                    .externalId(externalId)
                    .build();
            customerRepository.save(customer);
        } else {
            existing.setCustomerType(type);
            existing.setFullName(fullName);
            existing.setBusinessName(businessName);
            existing.setEmail(email);
            existing.setPhone(phone);
            customerRepository.save(existing);
        }
    }

    private CustomerType parseType(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return CustomerType.INDIVIDUAL;
        }
        try {
            return CustomerType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            String normalized = raw.trim().toLowerCase(Locale.ROOT);
            if (normalized.contains("business") || normalized.contains("company") || normalized.contains("org")) {
                return CustomerType.BUSINESS;
            }
            return CustomerType.INDIVIDUAL;
        }
    }

    private String trimOrNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

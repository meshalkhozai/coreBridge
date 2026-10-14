package com.corebridge.api.service;

import com.corebridge.api.dto.CustomerRequestDto;
import com.corebridge.api.dto.CustomerResponseDto;
import com.corebridge.api.entity.Customer;
import com.corebridge.api.entity.CustomerType;
import com.corebridge.api.exception.BadRequestException;
import com.corebridge.api.exception.ResourceNotFoundException;
import com.corebridge.api.repository.CustomerRepository;
import com.corebridge.api.util.ExternalIdNormalizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ExternalIdNormalizer externalIdNormalizer;

    public CustomerService(CustomerRepository customerRepository, ExternalIdNormalizer externalIdNormalizer) {
        this.customerRepository = customerRepository;
        this.externalIdNormalizer = externalIdNormalizer;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
        return CustomerResponseDto.fromEntity(customer);
    }

    public CustomerResponseDto create(CustomerRequestDto request) {
        validateName(request);
        String normalizedExternalId = externalIdNormalizer.normalize(request.getExternalId());

        Customer customer = Customer.builder()
                .customerType(request.getCustomerType())
                .fullName(trimOrNull(request.getFullName()))
                .businessName(trimOrNull(request.getBusinessName()))
                .email(trimOrNull(request.getEmail()))
                .phone(trimOrNull(request.getPhone()))
                .externalId(normalizedExternalId)
                .build();
        return CustomerResponseDto.fromEntity(customerRepository.save(customer));
    }

    public CustomerResponseDto update(Long id, CustomerRequestDto request) {
        validateName(request);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));

        customer.setCustomerType(request.getCustomerType());
        customer.setFullName(trimOrNull(request.getFullName()));
        customer.setBusinessName(trimOrNull(request.getBusinessName()));
        customer.setEmail(trimOrNull(request.getEmail()));
        customer.setPhone(trimOrNull(request.getPhone()));
        customer.setExternalId(externalIdNormalizer.normalize(request.getExternalId()));
        return CustomerResponseDto.fromEntity(customerRepository.save(customer));
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found: " + id);
        }
        customerRepository.deleteById(id);
    }

    private void validateName(CustomerRequestDto request) {
        if (request.getCustomerType() == CustomerType.INDIVIDUAL
                && (request.getFullName() == null || request.getFullName().trim().isEmpty())) {
            throw new BadRequestException("fullName is required for INDIVIDUAL customers");
        }
        if (request.getCustomerType() == CustomerType.BUSINESS
                && (request.getBusinessName() == null || request.getBusinessName().trim().isEmpty())) {
            throw new BadRequestException("businessName is required for BUSINESS customers");
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

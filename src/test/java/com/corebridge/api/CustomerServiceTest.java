package com.corebridge.api.service;

import com.corebridge.api.dto.CustomerRequestDto;
import com.corebridge.api.entity.Customer;
import com.corebridge.api.entity.CustomerType;
import com.corebridge.api.exception.BadRequestException;
import com.corebridge.api.exception.ResourceNotFoundException;
import com.corebridge.api.repository.CustomerRepository;
import com.corebridge.api.util.ExternalIdNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, new ExternalIdNormalizer());
    }

    @Test
    void createIndividualCustomer() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setCustomerType(CustomerType.INDIVIDUAL);
        request.setFullName("  Alice Carter  ");
        request.setEmail("alice@example.com");
        request.setExternalId(" ext-001 ");

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        var result = customerService.create(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo("Alice Carter");
        assertThat(result.getExternalId()).isEqualTo("EXT-001");
    }

    @Test
    void createBusinessWithoutNameThrows() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setCustomerType(CustomerType.BUSINESS);

        assertThatThrownBy(() -> customerService.create(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void findByIdMissingThrowsNotFound() {
        when(customerRepository.findById(42L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.findById(42L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteMissingThrowsNotFound() {
        when(customerRepository.existsById(7L)).thenReturn(false);
        assertThatThrownBy(() -> customerService.delete(7L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}

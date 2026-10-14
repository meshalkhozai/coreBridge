package com.corebridge.api.service;

import com.corebridge.api.dto.ExternalCustomerDto;
import com.corebridge.api.entity.Customer;
import com.corebridge.api.integration.ExternalProviderClient;
import com.corebridge.api.repository.CustomerRepository;
import com.corebridge.api.util.ExternalIdNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerImportServiceTest {

    @Mock
    private ExternalProviderClient externalProviderClient;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerImportService importService;

    @BeforeEach
    void setUp() {
        importService = new CustomerImportService(externalProviderClient, customerRepository, new ExternalIdNormalizer());
    }

    @Test
    void importsAllPayloadEntries() {
        ExternalCustomerDto one = new ExternalCustomerDto();
        one.setExternalId("ext-100");
        one.setCustomerType("INDIVIDUAL");
        one.setName("  Test User  ");

        ExternalCustomerDto two = new ExternalCustomerDto();
        two.setExternalId("BIZ-200");
        two.setCustomerType("BUSINESS");
        two.setBusinessName("Acme Test");

        when(externalProviderClient.fetchCustomers()).thenReturn(List.of(one, two));
        when(customerRepository.findByExternalId(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        int imported = importService.importCustomers();

        assertThat(imported).isEqualTo(2);
        verify(customerRepository, org.mockito.Mockito.times(2)).save(any(Customer.class));
    }
}

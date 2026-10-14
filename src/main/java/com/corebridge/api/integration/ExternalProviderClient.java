package com.corebridge.api.integration;

import com.corebridge.api.dto.ExternalCustomerDto;

import java.util.List;

public interface ExternalProviderClient {
    List<ExternalCustomerDto> fetchCustomers();
}

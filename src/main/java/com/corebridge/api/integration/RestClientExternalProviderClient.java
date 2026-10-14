package com.corebridge.api.integration;

import com.corebridge.api.dto.ExternalCustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class RestClientExternalProviderClient implements ExternalProviderClient {

    private final RestClient restClient;
    private final String importPath;

    public RestClientExternalProviderClient(
            @Value("${corebridge.integration.external-provider-base-url}") String baseUrl,
            @Value("${corebridge.integration.import-path}") String importPath) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
        this.importPath = importPath;
    }

    @Override
    public List<ExternalCustomerDto> fetchCustomers() {
        ExternalCustomerDto[] body = restClient.get()
                .uri(importPath)
                .retrieve()
                .body(ExternalCustomerDto[].class);
        if (body == null) {
            return List.of();
        }
        return Arrays.asList(body);
    }
}

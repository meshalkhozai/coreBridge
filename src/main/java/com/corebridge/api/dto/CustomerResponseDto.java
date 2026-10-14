package com.corebridge.api.dto;

import com.corebridge.api.entity.Customer;
import com.corebridge.api.entity.CustomerType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponseDto {

    private Long id;
    private CustomerType customerType;
    private String fullName;
    private String businessName;
    private String email;
    private String phone;
    private String externalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CustomerResponseDto fromEntity(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .customerType(customer.getCustomerType())
                .fullName(customer.getFullName())
                .businessName(customer.getBusinessName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .externalId(customer.getExternalId())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}

package com.corebridge.api.dto;

import com.corebridge.api.entity.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDto {

    @NotNull(message = "customerType is required")
    private CustomerType customerType;

    @Size(max = 255, message = "fullName must be at most 255 characters")
    private String fullName;

    @Size(max = 255, message = "businessName must be at most 255 characters")
    private String businessName;

    @Email(message = "email must be a valid email address")
    @Size(max = 255, message = "email must be at most 255 characters")
    private String email;

    @Size(max = 64, message = "phone must be at most 64 characters")
    private String phone;

    @Size(max = 128, message = "externalId must be at most 128 characters")
    private String externalId;
}

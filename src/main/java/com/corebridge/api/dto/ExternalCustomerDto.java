package com.corebridge.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalCustomerDto {

    @JsonProperty("external_id")
    @JsonAlias({"externalId", "id", "provider_customer_id", "remote_id"})
    private String externalId;

    @JsonProperty("customer_type")
    @JsonAlias({"type", "customerType", "kind"})
    private String customerType;

    @JsonProperty("name")
    @JsonAlias({"full_name", "fullName", "person_name", "contact_name"})
    private String name;

    @JsonProperty("business_name")
    @JsonAlias({"company", "company_name", "businessName", "organization"})
    private String businessName;

    @JsonProperty("email")
    @JsonAlias({"email_address", "mail"})
    private String email;

    @JsonProperty("phone")
    @JsonAlias({"phone_number", "telephone", "mobile"})
    private String phone;
}

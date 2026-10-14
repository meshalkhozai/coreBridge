package com.corebridge.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/external/mock")
public class ExternalProviderController {

    @GetMapping("/customers")
    public ResponseEntity<List<Map<String, Object>>> customers() {
        List<Map<String, Object>> payload = List.of(
                Map.of(
                        "provider_customer_id", "EXT-001",
                        "customerType", "INDIVIDUAL",
                        "full_name", "Alice Carter",
                        "mail", "alice.carter@example.com",
                        "phone_number", "+1-555-0101"
                ),
                Map.of(
                        "externalId", "EXT-002",
                        "type", "BUSINESS",
                        "company", "Acme Automation LLC",
                        "business_name", "Acme Automation LLC",
                        "email_address", "contact@acme.example",
                        "telephone", "+1-555-0102"
                ),
                Map.of(
                        "id", "ext-003",
                        "kind", "individual",
                        "contact_name", "  Bob   Smith  ",
                        "organization", "",
                        "mail", " BOB.SMITH@EXAMPLE.COM ",
                        "mobile", "+1-555-0103"
                )
        );
        return ResponseEntity.ok(payload);
    }
}

package com.corebridge.api.controller;

import com.corebridge.api.dto.CustomerRequestDto;
import com.corebridge.api.dto.CustomerResponseDto;
import com.corebridge.api.service.CustomerImportService;
import com.corebridge.api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerImportService customerImportService;

    public CustomerController(CustomerService customerService, CustomerImportService customerImportService) {
        this.customerService = customerService;
        this.customerImportService = customerImportService;
    }

    @GetMapping
    public List<CustomerResponseDto> list() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponseDto get(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@Valid @RequestBody CustomerRequestDto request) {
        CustomerResponseDto created = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable Long id, @Valid @RequestBody CustomerRequestDto request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<Map<String, Object>> importCustomers() {
        int imported = customerImportService.importCustomers();
        return ResponseEntity.ok(Map.of("importedCount", imported, "status", "completed"));
    }
}

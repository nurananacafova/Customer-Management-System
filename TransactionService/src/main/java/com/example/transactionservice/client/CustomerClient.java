package com.example.transactionservice.client;

import com.example.transactionservice.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "customer", url = "${customer.url}")
public interface CustomerClient {
    @GetMapping("customers/{id}")
    public CustomerDto getCustomerDetailsById(@PathVariable long id);

    @PutMapping("customers/update/{id}")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestBody Double newBalanc);
}

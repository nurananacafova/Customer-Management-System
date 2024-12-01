package com.example.transactionservice.client;

import com.example.transactionservice.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "customer-service", url = "${customer.url}")
public interface CustomerClient {
    @GetMapping("/{customerCif}")
    public CustomerDto getCustomerDetailsById(@PathVariable long customerCif);

    @PutMapping("/update/{customerCif}")
    void updateBalance(@PathVariable Long customerCif, @RequestBody Double newBalance);
}

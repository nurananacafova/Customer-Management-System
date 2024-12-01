package com.example.customerservice.client;

import com.example.customerservice.dto.TransactionHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "transaction-service", url = "${transaction.url}")
public interface TransactionClient {
    @GetMapping("/customer/{customerCif}")
    public List<TransactionHistoryDto> getTransactionsByCustomer(@PathVariable Long customerCif);
}

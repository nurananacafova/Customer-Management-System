package com.example.customerservice.client;

import com.example.customerservice.dto.TransactionHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "transaction", url = "${transaction.url}")
public interface TransactionClient {
    @GetMapping("/transactions/customer/{cif}")
    List<TransactionHistoryDto> getTransactionsByCustomer(@PathVariable Long cif);
}

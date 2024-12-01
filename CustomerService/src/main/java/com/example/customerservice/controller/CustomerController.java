package com.example.customerservice.controller;

import com.example.customerservice.dto.CustomerDto;
import com.example.customerservice.dto.TransactionHistoryDto;
import com.example.customerservice.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;


    @PostMapping("/")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{customerCif}")
    public ResponseEntity<CustomerDto> getCustomerDetailsById(@PathVariable long customerCif) {
        CustomerDto customerDto = customerService.getCustomerDetailsById(customerCif);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PutMapping("/update/{customerCif}")
    public ResponseEntity<String> updateBalance(@PathVariable Long customerCif, @RequestBody Double newBalance) {
        customerService.updateBalance(customerCif, newBalance);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transactions/{customerCif}")
    public ResponseEntity<List<TransactionHistoryDto>> getCustomerTransactions(@PathVariable Long customerCif) {
        List<TransactionHistoryDto> transactionHistoryDto=customerService.getCustomerTransactions(customerCif);
        return new ResponseEntity<>(transactionHistoryDto,HttpStatus.OK);
    }
}

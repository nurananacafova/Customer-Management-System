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
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;


    @PostMapping("/")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerDetailsById(@PathVariable long id) {
        CustomerDto customerDto = customerService.getCustomerDetailsById(id);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestBody Double newBalanc) {
        customerService.updateBalance(id, newBalanc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionHistoryDto>> getCustomerTransactions(@PathVariable Long customerCif) {
        customerService.getCustomerTransactions(customerCif);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

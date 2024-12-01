package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransactionHistoryDto;
import com.example.transactionservice.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @PostMapping("/top-up")
    public ResponseEntity<String> topUp(@RequestBody Long customerCIF, @RequestBody double amount) {
        transactionService.topUp(customerCIF, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@RequestBody Long customerCIF, @RequestBody double purchaseAmount) {
        transactionService.purchase(customerCIF, purchaseAmount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refund/{customerId}")
    public ResponseEntity<String> refund(@PathVariable Long customerId) {
        transactionService.refund(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/transactions/{customerId}")
//    public ResponseEntity<List<TransactionHistoryDto>> getTransactionHistoryByCustomer(@PathVariable Long customerId) {
//        List<TransactionHistoryDto> transactionHistoryDtos = transactionService.getTransactionHistoryByCustomer(customerId);
//        return new ResponseEntity<>(transactionHistoryDtos, HttpStatus.OK);
//    }

    @GetMapping("/transactions/customer/{cif}")
    public ResponseEntity<List<TransactionHistoryDto>> getTransactionsByCustomer(@PathVariable Long cif) {
        List<TransactionHistoryDto> transactionHistoryDtos = transactionService.getTransactionHistoryByCustomer(cif);
        return new ResponseEntity<>(transactionHistoryDtos, HttpStatus.OK);
    }
}




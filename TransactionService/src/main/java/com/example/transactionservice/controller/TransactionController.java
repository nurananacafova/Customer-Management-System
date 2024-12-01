package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransactionHistoryDto;
import com.example.transactionservice.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @PostMapping("/top-up/{customerCif}/{amount}")
    public ResponseEntity<String> topUp(@PathVariable Long customerCif, @PathVariable double amount) {
        transactionService.topUp(customerCif, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/purchase/{customerCif}/{amount}")
    public ResponseEntity<String> purchase(@PathVariable Long customerCif, @PathVariable double amount) {
        transactionService.purchase(customerCif, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refund/{customerCif}/{refundAmount}")
    public ResponseEntity<String> refund(@PathVariable Long customerCif, @PathVariable double refundAmount) {
        transactionService.refund(customerCif,refundAmount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/customer/{customerCif}")
    public ResponseEntity<List<TransactionHistoryDto>> getTransactionsByCustomer(@PathVariable Long customerCif) {
        List<TransactionHistoryDto> transactionHistoryDtos = transactionService.getTransactionHistoryByCustomer(customerCif);
        return new ResponseEntity<>(transactionHistoryDtos, HttpStatus.OK);
    }
}




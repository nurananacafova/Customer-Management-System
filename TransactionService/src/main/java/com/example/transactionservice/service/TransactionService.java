package com.example.transactionservice.service;


import com.example.transactionservice.dto.TransactionHistoryDto;

import java.util.List;

public interface TransactionService {
     void topUp(Long customerCIF, double amount);

     void purchase(Long customerCIF, double purchaseAmount);

     void refund(Long customerId);

     List<TransactionHistoryDto>getTransactionHistoryByCustomer(Long customerId);
}

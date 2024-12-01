package com.example.customerservice.dto;

import com.example.customerservice.enums.OperationType;
import com.example.customerservice.enums.TransactionStatus;
import com.example.customerservice.model.Customer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {
    private Long id;
    private Long customerCif;
    private OperationType operationType;
    private TransactionStatus transactionStatus;
    private Double amount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}

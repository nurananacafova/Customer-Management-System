package com.example.transactionservice.dto;

import com.example.transactionservice.enums.OperationType;
import com.example.transactionservice.enums.TransactionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {
    private Long id;
    private Customer customer;
    private OperationType operationType;
    private TransactionStatus transactionStatus;
    private Double amount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}

package com.example.transactionservice.dto;

import com.example.transactionservice.enums.OperationType;
import com.example.transactionservice.enums.TransactionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {
    private Long id;
    private Long customerCif;
    private OperationType operationType;
    private TransactionStatus transactionStatus;
    private Double balanceBefore;
    private Double balanceAfter;
    private Double transactionAmount;;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}

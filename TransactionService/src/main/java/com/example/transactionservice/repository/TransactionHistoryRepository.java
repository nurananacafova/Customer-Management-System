package com.example.transactionservice.repository;

import com.example.transactionservice.dto.CustomerDto;
import com.example.transactionservice.enums.OperationType;
import com.example.transactionservice.enums.TransactionStatus;
import com.example.transactionservice.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Optional<TransactionHistory> findTopByCustomerCifAndOperationTypeAndTransactionStatusOrderByCreateDateDesc(
            Long customerCif, OperationType operationType, TransactionStatus transactionStatus);

    List<TransactionHistory> findByCustomerCif(Long customerCif);
}

package com.example.transactionservice.repository;

import com.example.transactionservice.dto.CustomerDto;
import com.example.transactionservice.enums.OperationType;
import com.example.transactionservice.enums.TransactionStatus;
import com.example.transactionservice.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Optional<TransactionHistory> findTopByCustomerCifAndOperationTypeAndTransactionStatusOrderByCreateDateDesc(
            Long customerCif, OperationType operationType, TransactionStatus transactionStatus);

    List<TransactionHistory> findByCustomerCif(Long customerCif);

    @Query("SELECT COALESCE(SUM(th.transactionAmount), 0) " +
            "FROM TransactionHistory th " +
            "WHERE th.customerCif = :customerId " +
            "AND th.operationType = :operationType " +
            "AND th.transactionStatus = :transactionStatus " +
            "AND th.createDate > :createDate")
    double calculateTotalRefunded(@Param("customerId") Long customerId,
                                  @Param("createDate") LocalDateTime createDate,
                                  @Param("operationType") OperationType operationType,
                                  @Param("transactionStatus") TransactionStatus transactionStatus);

}

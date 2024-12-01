package com.example.transactionservice.service.impl;

import com.example.transactionservice.client.CustomerClient;
import com.example.transactionservice.dto.CustomerDto;
import com.example.transactionservice.exception.CustomerNotFoundException;
import com.example.transactionservice.exception.InsufficientBalanceException;
import com.example.transactionservice.exception.NoValidTransactionException;
import com.example.transactionservice.mapper.ModelMapper;
import com.example.transactionservice.dto.TransactionHistoryDto;
import com.example.transactionservice.enums.OperationType;
import com.example.transactionservice.enums.TransactionStatus;
import com.example.transactionservice.model.TransactionHistory;
import com.example.transactionservice.repository.TransactionHistoryRepository;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final ModelMapper modelMapper;
    private final CustomerClient customerClient;

    @Override
    @Transactional
    public void topUp(Long customerCIF, double amount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);
//        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerCIF);

        double newBalance = customer.getBalance() + amount;
        customerClient.updateBalance(customerCIF, newBalance);
        saveTransaction(customerCIF, amount, customer.getBalance(), newBalance, OperationType.TOP_UP, TransactionStatus.SUCCESS);
    }

    @Override
    @Transactional(noRollbackFor = InsufficientBalanceException.class)
    public void purchase(Long customerCIF, double purchaseAmount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);

        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerCIF);

        if (customer.getBalance() < purchaseAmount || customer.getBalance() <= 0) {
            saveTransaction(customerCIF, purchaseAmount, customer.getBalance(), customer.getBalance(), OperationType.PURCHASE, TransactionStatus.FAILED);
            throw new InsufficientBalanceException("Insufficient balance " + customer.getBalance());
        }

        double newBalance = customer.getBalance() - purchaseAmount;
        customerClient.updateBalance(customerCIF, newBalance);

        saveTransaction(customerCIF, purchaseAmount, customer.getBalance(), newBalance, OperationType.PURCHASE, TransactionStatus.SUCCESS);
    }

    @Override
    @Transactional(noRollbackFor = InsufficientBalanceException.class)
    public void refund(Long customerId, double refundAmount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerId);
        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerId);

        TransactionHistory lastValidPurchase = transactionHistoryRepository
                .findTopByCustomerCifAndOperationTypeAndTransactionStatusOrderByCreateDateDesc(
                        customerId, OperationType.PURCHASE, TransactionStatus.SUCCESS).orElseThrow(() ->
                        new NoValidTransactionException("No valid purchase found to refund."));

        double totalRefunded = transactionHistoryRepository.calculateTotalRefunded(customerId, lastValidPurchase.getCreateDate(), OperationType.REFUND, TransactionStatus.SUCCESS);
        double refundableAmount = lastValidPurchase.getTransactionAmount() - totalRefunded;

        if (refundAmount > refundableAmount) {
            saveTransaction(customerId, refundAmount, customer.getBalance(), customer.getBalance(), OperationType.REFUND, TransactionStatus.FAILED);
            throw new InsufficientBalanceException(
                    "Refund operation failed with customer ID: " + customerId +
                            "\nLast successful purchase balance: " + lastValidPurchase.getTransactionAmount() +
                            "\nRequested refund amount is " + refundAmount);
        }
        double newBalance = customer.getBalance() + refundAmount;
        customerClient.updateBalance(customerId, newBalance);

        saveTransaction(customerId, refundAmount, customer.getBalance(), newBalance, OperationType.REFUND, TransactionStatus.SUCCESS);

    }

    @Override
    public List<TransactionHistoryDto> getTransactionHistoryByCustomer(Long customerId) {
        List<TransactionHistory> transactionHistory = transactionHistoryRepository.findByCustomerCif(customerId);
        return modelMapper.toTransactionHistoryDTOList(transactionHistory);
    }

    private void saveTransaction(Long customerCif, double transactionAmount, double balanceBefore, double balanceAfter, OperationType operationType, TransactionStatus status) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setCustomerCif(customerCif);
        transactionHistory.setTransactionAmount(transactionAmount);
        transactionHistory.setBalanceBefore(balanceBefore);
        transactionHistory.setBalanceAfter(balanceAfter);
        transactionHistory.setOperationType(operationType);
        transactionHistory.setTransactionStatus(status);
        transactionHistoryRepository.save(transactionHistory);
    }
}

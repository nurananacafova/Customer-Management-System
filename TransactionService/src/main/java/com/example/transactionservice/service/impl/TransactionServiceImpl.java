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
    public void topUp(Long customerCIF, double amount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);
//        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerCIF);

        double newBalance = customer.getBalance() + amount;
        customerClient.updateBalance(customerCIF, newBalance);
        saveTransaction(customerCIF, amount, customer.getBalance(), newBalance, OperationType.TOP_UP, TransactionStatus.SUCCESS);
    }

    @Override
    public void purchase(Long customerCIF, double purchaseAmount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);

        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerCIF);

        if (customer.getBalance() < purchaseAmount) {
            saveTransaction(customerCIF, purchaseAmount, customer.getBalance(), customer.getBalance(), OperationType.PURCHASE, TransactionStatus.FAILED);
            throw new InsufficientBalanceException("Insufficient balance " + customer.getBalance());
        }

        double newBalance = customer.getBalance() - purchaseAmount;
        customerClient.updateBalance(customerCIF, newBalance);

        saveTransaction(customerCIF, purchaseAmount, customer.getBalance(), newBalance, OperationType.PURCHASE, TransactionStatus.SUCCESS);
    }

    @Override
    @Transactional
    public void refund(Long customerId, double refundAmount) {
        TransactionHistory lastValidPurchase = transactionHistoryRepository
                .findTopByCustomerCifAndOperationTypeAndTransactionStatusOrderByCreateDateDesc(
                        customerId, OperationType.PURCHASE, TransactionStatus.SUCCESS).orElseThrow(() ->
                        new NoValidTransactionException("No valid purchase found to refund."));

        CustomerDto customer = customerClient.getCustomerDetailsById(customerId);
        if (customer == null) throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        if (refundAmount > lastValidPurchase.getTransactionAmount()) {
            saveTransaction(customerId, refundAmount, customer.getBalance(), customer.getBalance(), OperationType.PURCHASE, TransactionStatus.FAILED);
            throw new InsufficientBalanceException("Insufficient balance " + refundAmount);
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

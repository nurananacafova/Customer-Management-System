package com.example.transactionservice.service.impl;

import com.example.transactionservice.client.CustomerClient;
import com.example.transactionservice.dto.CustomerDto;
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
    //    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerClient customerClient;

    @Override
    public void topUp(Long customerCIF, double amount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);
        if (customer == null) throw new RuntimeException("Customer not found");

        double newBalance = customer.getBalance() + amount;
        customerClient.updateBalance(customerCIF, newBalance);
        saveTransaction(customerCIF, amount, OperationType.TOP_UP, TransactionStatus.SUCCESS);


//        TransactionHistory transactionHistory = new TransactionHistory();
//        transactionHistory.setCustomer(customer);
//        transactionHistory.setAmount(amount);
//        transactionHistory.setTransactionStatus(TransactionStatus.SUCCESS);
//        transactionHistory.setOperationType(OperationType.TOP_UP);
//        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public void purchase(Long customerCIF, double purchaseAmount) {
        CustomerDto customer = customerClient.getCustomerDetailsById(customerCIF);

        if (customer == null) throw new RuntimeException("Customer not found");

        if (customer.getBalance() < purchaseAmount) {
            saveTransaction(customerCIF, purchaseAmount, OperationType.PURCHASE, TransactionStatus.FAILED);
            throw new RuntimeException("Insufficient balance");
        }

        double newBalance = customer.getBalance() - purchaseAmount;
        customerClient.updateBalance(customerCIF, newBalance);

        saveTransaction(customerCIF, purchaseAmount, OperationType.PURCHASE, TransactionStatus.SUCCESS);


//        TransactionHistory transactionHistory = new TransactionHistory();
//        Double balance = customer.getBalance();
//        if (balance < purchaseAmount) {
//            transactionHistory.setCustomer(customer);
//            transactionHistory.setOperationType(OperationType.PURCHASE);
//            transactionHistory.setAmount(purchaseAmount);
//            transactionHistory.setTransactionStatus(TransactionStatus.FAILED);
//            transactionHistoryRepository.save(transactionHistory);
//            throw new RuntimeException("Purchase amount exceeds balance");
//        }
//        Double newBalance = balance - purchaseAmount;
//        customer.setBalance(newBalance);
//        customerRepository.save(customer);
//
//
//        transactionHistory.setCustomer(customer);
//        transactionHistory.setOperationType(OperationType.PURCHASE);
//        transactionHistory.setAmount(purchaseAmount);
//        transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    @Transactional
    public void refund(Long customerId) {
        TransactionHistory lastValidPurchase = transactionHistoryRepository
                .findTopByCustomerCifAndOperationTypeAndTransactionStatusOrderByCreateDateDesc(
                        customerId, OperationType.PURCHASE, TransactionStatus.SUCCESS).orElseThrow(() ->
                        new RuntimeException("No valid purchase found to refund."));

//        if (lastValidPurchase.isEmpty()) {
//            throw new RuntimeException("No valid purchase found to refund.");
//        }

        saveTransaction(customerId, lastValidPurchase.getAmount(), OperationType.REFUND, TransactionStatus.SUCCESS);

        CustomerDto customer = customerClient.getCustomerDetailsById(customerId);
        double newBalance = customer.getBalance() + lastValidPurchase.getAmount();
        customerClient.updateBalance(customerId, newBalance);

//        TransactionHistory purchaseTransaction = lastValidPurchase.get();
//
//        // Step 2: Create the refund transaction (Refund the same amount)
//        TransactionHistory refundTransaction = new TransactionHistory();
//        refundTransaction.setCustomer(purchaseTransaction.getCustomer());
//        refundTransaction.setAmount(purchaseTransaction.getAmount()); // Refund the same amount
//        refundTransaction.setOperationType(OperationType.REFUND); // Set operation type to REFUND
//        refundTransaction.setTransactionStatus(TransactionStatus.SUCCESS); // Successful refund
//        refundTransaction.setCreateDate(LocalDateTime.now()); // Set the current date
//        refundTransaction.setUpdateDate(LocalDateTime.now()); // Set the current date
//
//        // Step 3: Save the refund transaction to the database
//        transactionHistoryRepository.save(refundTransaction);
//
//        // Step 4: Update the customer's balance (assuming the balance is part of Customer entity)
//        Customer customer = purchaseTransaction.getCustomer();
//        if (customer.getBalance() != null) {
//            customer.setBalance(customer.getBalance() + purchaseTransaction.getAmount()); // Refund to balance
//        } else {
//            customer.setBalance(purchaseTransaction.getAmount()); // If balance was null, set it to refunded amount
//        }
//
//        // Step 5: Save the updated customer balance
//        customerRepository.save(customer);
    }

    @Override
    public List<TransactionHistoryDto> getTransactionHistoryByCustomer(Long customerId) {
//        CustomerDto customer = customerClient.getCustomerDetailsById(customerId);
//        if (customer == null) {
//            throw new RuntimeException("Customer not found");
//        }
        List<TransactionHistory> transactionHistory = transactionHistoryRepository.findByCustomerCif(customerId);
        return modelMapper.toTransactionHistoryDTOList(transactionHistory);
    }

    private void saveTransaction(Long customerCif, double amount, OperationType operationType, TransactionStatus status) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setCustomerCif(customerCif);
        transactionHistory.setAmount(amount);
        transactionHistory.setOperationType(operationType);
        transactionHistory.setTransactionStatus(status);
        transactionHistoryRepository.save(transactionHistory);
    }
}

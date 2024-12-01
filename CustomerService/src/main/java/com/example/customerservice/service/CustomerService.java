package com.example.customerservice.service;


import com.example.customerservice.dto.CustomerDto;
import com.example.customerservice.dto.TransactionHistoryDto;

import java.util.List;

public interface CustomerService {
    void createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerDetailsById(long id);

    void updateBalance(Long customerCif, Double newBalance);

    List<TransactionHistoryDto> getCustomerTransactions(Long customerCif);
}

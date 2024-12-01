package com.example.customerservice.service.impl;

import com.example.customerservice.client.TransactionClient;
import com.example.customerservice.dto.CustomerDto;
import com.example.customerservice.dto.ModelMapper;
import com.example.customerservice.dto.TransactionHistoryDto;
import com.example.customerservice.model.Customer;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final TransactionClient transactionClient;

    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setBirthDate(customerDto.getBirthDate());
        customer.setBalance(50.0);
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomerDetailsById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return modelMapper.toDto(customer);
    }

    @Override
    public void updateBalance(Long customerCif, Double newBalance) {
        Customer customer = customerRepository.findById(customerCif)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setBalance(newBalance);
        customerRepository.save(customer);
    }

    @Override
    public List<TransactionHistoryDto> getCustomerTransactions(Long customerCif) {
//        Customer customer = customerRepository.findById(customerCif)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return transactionClient.getTransactionsByCustomer(customerCif);
    }
}
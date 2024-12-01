package com.example.transactionservice.dto;

import com.example.transactionservice.model.TransactionHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
//    CustomerDto toDto(Customer customer);
//    Customer toEntity(CustomerDto customerDto);
    List<TransactionHistoryDto> toTransactionHistoryDTOList(List<TransactionHistory> transactionHistoryList);
    List<TransactionHistory> toTransactionHistoryList(List<TransactionHistoryDto> transactionHistoryDtoList);
}

package com.example.transactionservice.mapper;

import com.example.transactionservice.dto.TransactionHistoryDto;
import com.example.transactionservice.model.TransactionHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    List<TransactionHistoryDto> toTransactionHistoryDTOList(List<TransactionHistory> transactionHistoryList);
    List<TransactionHistory> toTransactionHistoryList(List<TransactionHistoryDto> transactionHistoryDtoList);
}

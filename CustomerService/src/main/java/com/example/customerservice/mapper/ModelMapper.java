package com.example.customerservice.mapper;

import com.example.customerservice.dto.CustomerDto;
import com.example.customerservice.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}

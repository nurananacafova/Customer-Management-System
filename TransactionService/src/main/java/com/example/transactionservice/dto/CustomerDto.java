package com.example.transactionservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private Long cif;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Double balance;
}

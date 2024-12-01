package com.example.customerservice.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CustomerDto {
    private Long cif;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Double balance;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}

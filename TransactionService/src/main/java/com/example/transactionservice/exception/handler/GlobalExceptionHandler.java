package com.example.transactionservice.exception.handler;

import com.example.transactionservice.exception.CustomerNotFoundException;
import com.example.transactionservice.exception.InsufficientBalanceException;
import com.example.transactionservice.exception.NoValidTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoValidTransactionException.class})
    public ResponseEntity<Object> handleNoValidTransactionException(NoValidTransactionException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    public ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

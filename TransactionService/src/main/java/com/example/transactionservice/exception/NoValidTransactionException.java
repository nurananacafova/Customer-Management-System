package com.example.transactionservice.exception;

public class NoValidTransactionException extends RuntimeException {
    public NoValidTransactionException(String string) {
        super(string);
    }
}

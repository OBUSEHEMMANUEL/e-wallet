package com.example.ewallet.utils;

public class InvalidCreditCardNumberException extends RuntimeException {

    public InvalidCreditCardNumberException(String message) {
        super(message);
    }
}

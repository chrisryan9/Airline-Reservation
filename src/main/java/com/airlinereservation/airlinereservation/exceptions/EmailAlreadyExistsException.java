package com.airlinereservation.airlinereservation.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

package com.breed.breed.domain;

import org.springframework.http.HttpStatus;

public class InvalidBreedException extends RuntimeException {
    public InvalidBreedException(String message) {
        super(message);
    }
}

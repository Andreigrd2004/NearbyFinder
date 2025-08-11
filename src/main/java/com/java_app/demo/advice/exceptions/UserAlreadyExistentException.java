package com.java_app.demo.advice.exceptions;

public class UserAlreadyExistentException extends RuntimeException {
    public UserAlreadyExistentException(String message) {
        super(message);
    }
}

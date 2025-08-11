package com.java_app.demo.advice.exceptions;

public class ApiKeyAlreadyExistentException extends RuntimeException{
    public ApiKeyAlreadyExistentException(String message) {
        super(message);
    }
}

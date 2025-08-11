package com.java_app.demo.advice.exceptions;

public class InexistentApiKeyException extends RuntimeException {
    public InexistentApiKeyException(String message) {
    super(message);
    }
}

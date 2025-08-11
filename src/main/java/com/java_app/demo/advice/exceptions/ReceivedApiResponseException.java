package com.java_app.demo.advice.exceptions;

public class ReceivedApiResponseException extends RuntimeException {
    public ReceivedApiResponseException(String message) {
        super(message);
    }
}

package com.java_app.demo.advice.exceptions;

public class InexistentUserException extends RuntimeException{
    public InexistentUserException(String message){
        super(message);
    }
}

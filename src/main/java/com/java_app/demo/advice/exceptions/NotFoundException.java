package com.java_app.demo.advice.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
      super(message);
  }
}

package com.java_app.demo.advice;

import com.java_app.demo.advice.exceptions.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

  @ExceptionHandler(InexistentUserException.class)
  public ResponseEntity<String> handleInexistentUserException(InexistentUserException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InexistentApiKeyException.class)
  public ResponseEntity<String> handleInexistentApiKeyException(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyExistentException.class)
  public ResponseEntity<String> handleUserAlreadyExistentException(UserAlreadyExistentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ApiKeyAlreadyExistentException.class)
  public ResponseEntity<String> handleApiKeyAlreadyExistentException(ApiKeyAlreadyExistentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(FormatNotRespectedException.class)
  public  ResponseEntity<String> handleFormatNotRespectedException(FormatNotRespectedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ReceivedApiResponseException.class)
  public ResponseEntity<String> handleReceivedApiResponseException(ReceivedApiResponseException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

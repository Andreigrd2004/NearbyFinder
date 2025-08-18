package com.java_app.demo.advice;

import com.java_app.demo.advice.exceptions.*;
import com.java_app.demo.advice.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFoundException(Exception ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleBadRequestException(BadRequestException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(InternalServerErrorException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleInternalServerErrorException(InternalServerErrorException ex) {
    return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    List<ErrorResponse> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessage = violation.getMessage();
      errors.add(
          new ErrorResponse(
              HttpStatus.BAD_REQUEST.value(),
              String.format(
                  "%s: %s", fieldName.substring(fieldName.lastIndexOf('.') + 1), errorMessage)));
    }
    return errors;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ErrorResponse> errors = new ArrayList<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.add(
                  new ErrorResponse(
                      HttpStatus.BAD_REQUEST.value(),
                      String.format("%s: %s", fieldName, errorMessage)));
            });
    return errors;
  }
}

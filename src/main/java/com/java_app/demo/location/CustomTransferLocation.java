package com.java_app.demo.location;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomTransferLocation {
  private LocationDto locationDto;
  private final HttpStatus httpStatus;
  private String message;

  public CustomTransferLocation(LocationDto locationDto, HttpStatus httpStatus) {
    this.locationDto = locationDto;
    this.httpStatus = httpStatus;
  }

  public CustomTransferLocation(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}

package com.java_app.demo.admin.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IpValidator implements ConstraintValidator<Ip, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String PATTERN =
        "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

    return value.matches(PATTERN);
  }
}

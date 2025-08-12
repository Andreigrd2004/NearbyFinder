package com.java_app.demo.admin.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IpValidator implements ConstraintValidator<Ip, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return InetAddressValidator.getInstance().isValid(value);
    }
}

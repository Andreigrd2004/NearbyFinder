package com.java_app.demo.admin.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IpValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Ip {
    String message() default "Invalid ip address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

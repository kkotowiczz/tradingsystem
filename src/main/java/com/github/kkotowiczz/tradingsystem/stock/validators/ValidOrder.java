package com.github.kkotowiczz.tradingsystem.stock.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Constraint(validatedBy = OrderValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrder {
    String message() default "Order is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
}

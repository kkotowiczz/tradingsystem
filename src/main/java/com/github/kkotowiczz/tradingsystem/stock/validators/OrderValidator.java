package com.github.kkotowiczz.tradingsystem.stock.validators;

import com.github.kkotowiczz.tradingsystem.stock.domain.OrderType;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class OrderValidator implements ConstraintValidator<ValidOrder, CreateOrderDto> {

    @Override
    public boolean isValid(CreateOrderDto createOrderDto, ConstraintValidatorContext constraintValidatorContext) {
        var valid = (createOrderDto.orderType() == OrderType.LMT && Objects.nonNull(createOrderDto.limitPrice()))
                || (createOrderDto.orderType() != OrderType.LMT && Objects.isNull(createOrderDto.limitPrice()));


        if(!valid) {
            var message = createOrderDto.orderType() == OrderType.LMT
                    ? "LMT order requires limit price value"
                    : String.format("%s order cannot have limit price value", createOrderDto.orderType());
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("limitPrice")
                    .addConstraintViolation();
        }

        return valid;
    }
}

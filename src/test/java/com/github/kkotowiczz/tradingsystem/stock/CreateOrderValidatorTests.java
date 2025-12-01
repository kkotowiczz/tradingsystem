package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.Currency;
import com.github.kkotowiczz.tradingsystem.stock.domain.OrderType;
import com.github.kkotowiczz.tradingsystem.stock.domain.TradingSide;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class CreateOrderValidatorTests {

    private Validator validator;
    CreateOrderValidatorTests() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @ParameterizedTest
    @EnumSource(value = OrderType.class, names = {"PCR", "PKC"})
    void shouldViolateConstraint_whenOrderTypesArePcrAndPkcAndOrderHasLimitPrice(OrderType type) {
        var dto = new CreateOrderDto(
                "ddd",
                TradingSide.BUY,
                Currency.PLN,
                1234L,
                Instant.now().plusSeconds(60*60),
                type,
                23L
        );

        Set<ConstraintViolation<CreateOrderDto>> validationResults = validator.validate(dto);
        Assertions.assertFalse(validationResults.isEmpty());
        assertThat(validationResults)
                .extracting(ConstraintViolation::getMessage)
                .contains(String.format("%s order cannot have limit price value", type));
    }

    @ParameterizedTest
    @EnumSource(value = OrderType.class, names = {"PCR", "PKC"})
    void shouldNotViolateConstraint_whenOrderTypesArePcrAndPkcAndOrderHasNotGotLimitPrice(OrderType type) {
        var dto = new CreateOrderDto(
                "ddd",
                TradingSide.BUY,
                Currency.PLN,
                1234L,
                Instant.now().plusSeconds(60*60),
                type,
                null
        );

        Set<ConstraintViolation<CreateOrderDto>> validationResults = validator.validate(dto);
        Assertions.assertTrue(validationResults.isEmpty());
    }

    @Test
    void shouldNotViolateConstraintWhenOrderTypeIsLmtAndOrderHasLimitPrice() {
        var dto = new CreateOrderDto(
                "ddd",
                TradingSide.BUY,
                Currency.PLN,
                1234L,
                Instant.now().plusSeconds(60*60),
                OrderType.LMT,
                324L
        );

        Set<ConstraintViolation<CreateOrderDto>> validationResults = validator.validate(dto);
        Assertions.assertTrue(validationResults.isEmpty());
    }

    @Test
    void shouldViolateConstraintWhenOrderTypeIsLmtAndOrderHasNotGotLimitPrice() {
        var dto = new CreateOrderDto(
                "ddd",
                TradingSide.BUY,
                Currency.PLN,
                1234L,
                Instant.now().plusSeconds(60*60),
                OrderType.LMT,
                null
        );

        Set<ConstraintViolation<CreateOrderDto>> validationResults = validator.validate(dto);
        Assertions.assertFalse(validationResults.isEmpty());
        assertThat(validationResults)
                .extracting(ConstraintViolation::getMessage)
                .contains(String.format("%s order requires limit price value", OrderType.LMT));
    }
}

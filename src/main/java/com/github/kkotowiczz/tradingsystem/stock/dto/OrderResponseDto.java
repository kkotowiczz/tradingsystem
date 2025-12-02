package com.github.kkotowiczz.tradingsystem.stock.dto;

import com.github.kkotowiczz.tradingsystem.stock.domain.Currency;
import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;
import com.github.kkotowiczz.tradingsystem.stock.domain.TradingSide;

import java.time.Instant;

public record OrderResponseDto(
        Long orderId,
        OrderStatus status,
        String isin,
        TradingSide side,
        Currency currency,
        Long quantity,
        Instant registrationTime,
        Instant executedTime
) {}

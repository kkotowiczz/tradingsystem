package com.github.kkotowiczz.tradingsystem.stock.dto;

import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;

import java.math.BigInteger;
import java.time.Instant;

public record OrderResponseDto(
    BigInteger orderId,
    OrderStatus status,
    Instant registrationTime
)
{
}

package com.github.kkotowiczz.tradingsystem.stock.dto;

import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;

import java.time.Instant;

public record OrderCreatedResponseDto(
        Long orderId,
        OrderStatus status,
        Instant registrationTime
)
{
}

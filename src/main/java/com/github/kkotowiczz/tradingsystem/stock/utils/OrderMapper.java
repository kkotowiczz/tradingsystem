package com.github.kkotowiczz.tradingsystem.stock.utils;

import com.github.kkotowiczz.tradingsystem.stock.domain.Order;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;

import java.math.BigInteger;
import java.time.Instant;

public class OrderMapper {
    public static Order fromCreateOrderDto(CreateOrderDto createOrderDto, BigInteger accountId) {
        return new Order(
                null,
                accountId,
                createOrderDto.isin(),
                createOrderDto.side(),
                createOrderDto.currency(),
                createOrderDto.quantity(),
                null,
                Instant.now()
        );
    }

//    public static
}

package com.github.kkotowiczz.tradingsystem.stock.utils;

import com.github.kkotowiczz.tradingsystem.stock.domain.Order;
import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderCreatedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.messaging.OrderCreatedEvent;

import java.time.Instant;

public class OrderMapper {
    public static Order fromCreateOrderDto(CreateOrderDto createOrderDto, Long accountId) {
        return new Order(
                null,
                accountId,
                createOrderDto.isin(),
                createOrderDto.side(),
                createOrderDto.currency(),
                OrderStatus.SUBMITTED,
                createOrderDto.quantity(),
                null,
                null
        );
    }
    public static OrderResponseDto toOrderResponseDto(Order order) {
        return new OrderResponseDto(
            order.getOrderId(),
                order.getOrderStatus(),
                order.getIsin(),
                order.getSide(),
                order.getCurrency(),
                order.getQuantity(),
                order.getRegistrationTime(),
                order.getExecutedTime()
        );
    }

    public static OrderCreatedEvent orderToOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(
                order.getOrderId(),
                Instant.now()
        );
    }
}

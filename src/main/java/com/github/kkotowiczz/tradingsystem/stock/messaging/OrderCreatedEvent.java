package com.github.kkotowiczz.tradingsystem.stock.messaging;

import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Instant registrationTime
) {
}

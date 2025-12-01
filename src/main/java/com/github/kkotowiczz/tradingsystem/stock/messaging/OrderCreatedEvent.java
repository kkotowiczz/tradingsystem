package com.github.kkotowiczz.tradingsystem.stock.messaging;

import java.math.BigInteger;
import java.time.Instant;

public class OrderCreatedEvent {
    private BigInteger orderId;
    private Instant registrationTime;

    public OrderCreatedEvent() {

    }

    public OrderCreatedEvent(BigInteger orderId, Instant registrationTime) {
        this.orderId = orderId;
        this.registrationTime = registrationTime;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", registrationTime=" + registrationTime +
                '}';
    }
}

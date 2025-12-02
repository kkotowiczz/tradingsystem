package com.github.kkotowiczz.tradingsystem.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Entity
@Table("ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", columnDefinition = "BIGINT")
    private Long orderId;

    @Column(name = "ACCOUNT_ID", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(name = "ISIN", columnDefinition = "VARCHAR(50)", nullable = false)
    private String isin;

    @Column(name = "SIDE", columnDefinition = "VARCHAR(10)", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradingSide side;

    @Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "QUANTITY", columnDefinition = "BIGINT")
    private Long quantity;

    @Column(name = "EXECUTION_TIME", columnDefinition = "TIMESTAMP")
    private Instant executedTime;

    @Column(name = "REGISTRATION_TIME", columnDefinition = "TIMESTAMP")
    private Instant registrationTime;

    public Order() {}

    public Order(Long orderId, Long accountId, String isin, TradingSide side, Currency currency, OrderStatus orderStatus, Long quantity, Instant executedTime, Instant registrationTime) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.isin = isin;
        this.side = side;
        this.currency = currency;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.executedTime = executedTime;
        this.registrationTime = registrationTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public TradingSide getSide() {
        return side;
    }

    public void setSide(TradingSide side) {
        this.side = side;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Instant getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Instant executedTime) {
        this.executedTime = executedTime;
    }

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }
}

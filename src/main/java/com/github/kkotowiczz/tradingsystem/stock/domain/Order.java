package com.github.kkotowiczz.tradingsystem.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ORDER_ID", columnDefinition = "BIGINT")
    private BigInteger orderId;

    @Column(name = "ACCOUNT_ID", columnDefinition = "BIGINT", nullable = false)
    private BigInteger accountId;

    @Column(name = "ISIN", columnDefinition = "VARCHAR(50)", nullable = false)
    private String isin;

    @Column(name = "SIDE", columnDefinition = "VARCHAR(10)", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradingSide side;

    @Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
    @Enumerated(EnumType.STRING)
    private Currency tradeCurrency;

    @Column(name = "QUANTITY", columnDefinition = "BIGINT")
    private Long quantity;

    @Column(name = "EXECUTION_TIME", columnDefinition = "TIMESTAMP")
    private Instant executedTime;

    @Column(name = "REGISTRATION_TIME", columnDefinition = "TIMESTAMP")
    private Instant registrationTime;

    public Order() {}

    public Order(BigInteger orderId, BigInteger accountId, String isin, TradingSide side, Currency tradeCurrency, Long quantity, Instant executedTime, Instant registrationTime) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.isin = isin;
        this.side = side;
        this.tradeCurrency = tradeCurrency;
        this.quantity = quantity;
        this.executedTime = executedTime;
        this.registrationTime = registrationTime;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
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

    public Currency getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(Currency tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
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

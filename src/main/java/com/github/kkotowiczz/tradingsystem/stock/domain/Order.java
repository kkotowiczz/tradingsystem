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

    @Column(name = "ACCOUNT_ID", columnDefinition = "BIGINT")
    private BigInteger accountId;

    @Column(name = "ISIN", columnDefinition = "VARCHAR(50)")
    private String isin;

    @Column(name = "SIDE", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private TradingSide side;

    @Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
    @Enumerated(EnumType.STRING)
    private Currency tradeCurrency;

    @Column(name = "QUANTITY", columnDefinition = "BIGINT")
    private Long quantity;

    @Column(name = "EXPIRES_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant expiresAt;

    @Column(name = "ORDER_TYPE", columnDefinition = "VARCHAR(3)")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;


}

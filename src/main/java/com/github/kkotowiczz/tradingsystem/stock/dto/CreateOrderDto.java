package com.github.kkotowiczz.tradingsystem.stock.dto;

import com.github.kkotowiczz.tradingsystem.stock.domain.Currency;
import com.github.kkotowiczz.tradingsystem.stock.domain.OrderType;
import com.github.kkotowiczz.tradingsystem.stock.domain.TradingSide;
import com.github.kkotowiczz.tradingsystem.stock.validators.ValidOrder;

import java.time.Instant;
import java.util.Objects;

@ValidOrder
public record CreateOrderDto(String isin, TradingSide side, Currency currency, Long quantity, Instant expiresAt,
                             OrderType orderType, Long limitPrice) {

    @Override
    public String toString() {
        return "CreateOrderDto[" +
                "isin=" + isin + ", " +
                "side=" + side + ", " +
                "currency=" + currency + ", " +
                "quantity=" + quantity + ", " +
                "expiresAt=" + expiresAt + ", " +
                "orderType=" + orderType + ", " +
                "limitPrice=" + limitPrice + ']';
    }


}

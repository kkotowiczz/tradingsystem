package com.github.kkotowiczz.tradingsystem.stock.http;

import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;

public class StockHttpClientFactory {
    public static StockHttpClient getStockClient(StockExchange stockExchange) {
        return switch (stockExchange) {
            case GPW -> new GpwHttpClient();
        };
    }
}

package com.github.kkotowiczz.tradingsystem.stock.http;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract sealed class StockHttpClient permits GpwHttpClient {
    protected String baseUrl;
    protected String tickersEndpoint;
    protected String pricesEndpoint;
    protected String orderEndpoint;

    public StockHttpClient(String baseUrl, String tickersEndpoint, String pricesEndpoint, String orderEndpoint) {
        this.baseUrl = baseUrl;
        this.tickersEndpoint = tickersEndpoint;
        this.pricesEndpoint = pricesEndpoint;
        this.orderEndpoint = orderEndpoint;
    }

    public abstract Mono<WrappedResponseDto<TickerDto>> getTickers();
    public abstract Mono<WrappedResponseDto<PriceDto>> getPrices();
    public abstract WebClient getWebClient();
}

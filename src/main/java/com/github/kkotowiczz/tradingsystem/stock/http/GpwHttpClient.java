package com.github.kkotowiczz.tradingsystem.stock.http;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public final class GpwHttpClient extends StockHttpClient {

    public GpwHttpClient() {
        super(
                System.getenv("WIREMOCK_URL") != null ? System.getenv("WIREMOCK_URL") : "http://localhost:3001",
                "tickers",
                "prices",
                "orders"
        );
    }

    private WebClient webClient = WebClient.builder()
            .baseUrl(super.baseUrl)
            .build();

    @Override
    public Mono<WrappedResponseDto<TickerDto>> getTickers() {
        return webClient
                .get()
                .uri(tickersEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<>() {}));
    }

    @Override
    public Mono<WrappedResponseDto<PriceDto>> getPrices() {
        return webClient
                .get()
                .uri(pricesEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<>() {}));
    }

    @Override
    public WebClient getWebClient() {
        return webClient;
    }
    
}

package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "/{stock}/tickers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<WrappedResponseDto<TickerDto>>> getTickers(@PathVariable("stock") StockExchange stock) {

        return stockService
                .getTickers(stock)
                .flatMap(responseDto -> Mono.just(ResponseEntity.ok().body(responseDto)));
    }

    @GetMapping(value = "/{stock}/prices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<WrappedResponseDto<PriceDto>>> getPrices(@PathVariable("stock") StockExchange stock) {

        return stockService
                .getPrices(stock)
                .flatMap(responseDto -> Mono.just(ResponseEntity.ok().body(responseDto)));
    }
}

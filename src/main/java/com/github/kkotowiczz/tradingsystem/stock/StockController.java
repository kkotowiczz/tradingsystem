package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;
import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

    @PostMapping(value = "/{stock}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OrderResponseDto>> createOrder(
            @PathVariable("stock") StockExchange stock,
            @Valid @RequestBody CreateOrderDto createOrderDto
    ) {
        System.out.println(createOrderDto);
        stockService.createOrder(createOrderDto);
        return Mono.just(ResponseEntity.ok(new OrderResponseDto(BigInteger.valueOf(2L), OrderStatus.SUBMITTED, Instant.now().truncatedTo(ChronoUnit.SECONDS))));
    }
}

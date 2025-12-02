package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderCreatedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.mocks.JwtProviderMock;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                .map(responseDto -> ResponseEntity.ok().body(responseDto));
    }

    @GetMapping(value = "/{stock}/prices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<WrappedResponseDto<PriceDto>>> getPrices(@PathVariable("stock") StockExchange stock) {

        return stockService
                .getPrices(stock)
                .map(responseDto -> ResponseEntity.ok().body(responseDto));
    }

    @PostMapping(value = "/{stock}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OrderCreatedResponseDto>> createOrder(
            @PathVariable("stock") StockExchange stock,
            @Valid @RequestBody CreateOrderDto createOrderDto
    ) {
        Long accountId = JwtProviderMock.getAccountId("jwt");

        return stockService
                .createOrder(createOrderDto, accountId)
                .map(ResponseEntity::ok);

    }

    @GetMapping(value = "/{stock}/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OrderResponseDto>> getOrderById(@PathVariable("stock") StockExchange stock, @PathVariable("id") Long id) {
        Long accountId = JwtProviderMock.getAccountId("jwt");
        return stockService.findById(id, accountId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}

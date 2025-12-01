package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.http.StockHttpClientFactory;
import com.github.kkotowiczz.tradingsystem.stock.messaging.OrderCreatedEvent;
import com.github.kkotowiczz.tradingsystem.stock.messaging.OrderEventProducer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.Instant;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final OrderEventProducer orderEventProducer;
    private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;

    public StockService(StockRepository stockRepository, OrderEventProducer orderEventProducer) {
        this.stockRepository = stockRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public Mono<WrappedResponseDto<TickerDto>> getTickers(StockExchange stock) {
        var stockWebClient = StockHttpClientFactory.getStockClient(stock);

        return stockWebClient.getTickers();
    }



    @Cacheable(value = "prices", key = "#stock")
    public Mono<WrappedResponseDto<PriceDto>> getPrices(StockExchange stock) {
        var stockWebClient = StockHttpClientFactory.getStockClient(stock);

        return stockWebClient.getPrices();
    }

    public Mono<WrappedResponseDto<?>> createOrder(CreateOrderDto dto) {
        orderEventProducer.sendOrderCreatedEvent(new OrderCreatedEvent(BigInteger.ONE, Instant.now()));
        return null;
    }

    @CacheEvict(value = "prices", allEntries = true)
    @Scheduled(fixedRate = StockService.FIFTEEN_MINUTES)
    public void clearPricesCache() {
        System.out.println("Cleared prices cache");
    }
}

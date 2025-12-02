package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.Order;
import com.github.kkotowiczz.tradingsystem.stock.domain.OrderStatus;
import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderCreatedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.OrderResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.http.StockHttpClientFactory;
import com.github.kkotowiczz.tradingsystem.stock.messaging.OrderCreatedEvent;
import com.github.kkotowiczz.tradingsystem.stock.messaging.OrderEventProducer;
import com.github.kkotowiczz.tradingsystem.stock.utils.OrderMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    public Mono<OrderCreatedResponseDto> createOrder(CreateOrderDto dto, Long accountId) {
        Order entity = OrderMapper.fromCreateOrderDto(dto, accountId);
        return stockRepository
                .save(entity)
                .flatMap(order -> Mono.zip(Mono.just(order), Mono.fromFuture(orderEventProducer.sendOrderCreatedEvent(new OrderCreatedEvent(order.getOrderId(), Instant.now())))))
                .flatMap(tuple -> Mono.just(new OrderCreatedResponseDto(tuple.getT1().getOrderId(), OrderStatus.SUBMITTED, Instant.now())));
    }

    public Mono<OrderResponseDto> findById(Long orderId, Long accountId) {
        return stockRepository.findByOrderIdAndAccountId(orderId, accountId)
                .map(OrderMapper::toOrderResponseDto);
    }

    @CacheEvict(value = "prices", allEntries = true)
    @Scheduled(fixedRate = StockService.FIFTEEN_MINUTES)
    public void clearPricesCache() {
        System.out.println("Cleared prices cache");
    }
}

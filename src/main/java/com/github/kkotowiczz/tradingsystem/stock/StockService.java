package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.StockExchange;
import com.github.kkotowiczz.tradingsystem.stock.dto.CreateOrderDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.http.StockHttpClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
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
        return null;
    }

    @CacheEvict(value = "prices", allEntries = true)
    @Scheduled(fixedRate = StockService.FIFTEEN_MINUTES)
    public void clearPricesCache() {
        System.out.println("Cleared prices cache");
    }
}

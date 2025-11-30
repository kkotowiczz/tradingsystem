package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.TickerDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.kkotowiczz.tradingsystem.stock.http.StockHttpClientFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class StockService {

    private static final int FIFTEEN_MINUTES = 15 * 60 * 1000;
    public Mono<WrappedResponseDto<TickerDto>> getTickers(StockExchange stock) {
        var stockWebClient = StockHttpClientFactory.getStockClient(stock);

        return stockWebClient.getTickers();
    }



    @Cacheable(value = "prices", key = "#stock")
    public Mono<WrappedResponseDto<PriceDto>> getPrices(StockExchange stock) {
        var stockWebClient = StockHttpClientFactory.getStockClient(stock);
        var random = new Random();

        return stockWebClient.getPrices();
    }

    @CacheEvict(value = "prices", allEntries = true)
    @Scheduled(fixedRate = StockService.FIFTEEN_MINUTES)
    public void clearPricesCache() {
        System.out.println("Cleared prices cache");
    }
}

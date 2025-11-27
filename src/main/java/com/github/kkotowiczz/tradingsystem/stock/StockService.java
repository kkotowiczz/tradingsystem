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

import java.time.Duration;
import java.util.List;

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

        // We're returning hardcoded value in order to showcase the caching functionality
        return Mono.just(new WrappedResponseDto<>(List.of(new PriceDto("322", 314l)))).cache(Duration.ofMinutes(15L));
    }

    @CacheEvict(value = "prices", allEntries = true)
    @Scheduled(fixedRate = StockService.FIFTEEN_MINUTES)
    public void clearPricesCache() {
        System.out.println("Cleared prices cache");
    }
}

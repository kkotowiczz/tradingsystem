package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.wiremock.spring.EnableWireMock;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableWireMock
public class StockCacheTest {

    private WireMockServer wireMockServer;
    private int port = 3050;
    private static StockController controller;

    @Autowired
    private RedisCacheManager cacheManager;
    @BeforeAll
    public static void init() {
        controller = new StockController(service);
    }

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(port));
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
    }

    private static StockService service = Mockito.mock(StockService.class);


    @Test
    public void testCache() {
        when(service.getPrices(StockExchange.GPW)).thenReturn(Mono.just(new WrappedResponseDto<>(List.of(new PriceDto("PLBSK0000017", 314)))));

        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);
        controller.getPrices(StockExchange.GPW);


        verify(service, times(1)).getPrices(eq(StockExchange.GPW));
    }
}

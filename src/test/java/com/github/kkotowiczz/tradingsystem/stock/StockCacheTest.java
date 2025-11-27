package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class StockCacheTest {

    @Autowired
    private StockService stockService;
    @Autowired
    private CacheManager redisCacheManager;

    private static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"));

    @BeforeAll
    public static void init() {
        redis.setPortBindings(List.of("5555:6379"));
        redis.start();
    }

    @AfterEach
    public void clearCache() {
        redisCacheManager.getCache("prices").clear();
    }

    @AfterAll
    public static void tearDown() {
        redis.close();
    }

    @Test
    public void testCache_objectExistsInRedis() {
        stockService.getPrices(StockExchange.GPW).block();

        var store = redisCacheManager.getCache("prices");
        var element = store.get(StockExchange.GPW.toString(), WrappedResponseDto.class);

        Assertions.assertNotNull(element);
        Assertions.assertFalse(element.getResults().isEmpty());

        var price = (PriceDto) element.getResults().get(0);
        Assertions.assertEquals("PLBSK0000017", price.getIsin());
        Assertions.assertTrue(price.getPrice() > 0);
    }
}

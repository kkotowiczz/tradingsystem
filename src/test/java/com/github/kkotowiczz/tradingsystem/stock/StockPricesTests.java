package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.PriceDto;
import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.wiremock.spring.EnableWireMock;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
@EnableWireMock
public class StockPricesTests {

    private WireMockServer wireMockServer;
    private int port = 3001;

    @Autowired
    private StockService stockService;
    @Autowired
    private CacheManager redisCacheManager;
    @Autowired
    private WebTestClient webTestClient;

    private static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"));

    @BeforeAll
    public static void init() {
        redis.setPortBindings(List.of("5555:6379"));
        redis.start();
    }

    @BeforeEach
    public void configureWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(port));
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
    }



    @AfterEach
    public void clearCache() {
        redisCacheManager.getCache("prices").clear();
        wireMockServer.stop();
    }

    @AfterAll
    public static void tearDown() {
        redis.close();
    }

    @Test
    public void testCache_objectExistsInRedis() {
        var response = """
        {
            "results": [
                {
                    "isin": "PLBSK0000017",
                    "price": 314
                }
            ]
        }
        """;


        stubFor(WireMock.get(("/prices"))
                .willReturn(okJson(response)));

        stockService.getPrices(StockExchange.GPW).block();

        var store = redisCacheManager.getCache("prices");
        var element = store.get(StockExchange.GPW.toString(), WrappedResponseDto.class);

        Assertions.assertNotNull(element);
        Assertions.assertFalse(element.getResults().isEmpty());

        var price = (PriceDto) element.getResults().get(0);
        Assertions.assertEquals("PLBSK0000017", price.getIsin());
        Assertions.assertTrue(price.getPrice() > 0);
    }

    @Test
    void getPrices() {
        var response = """
                {
                  "results": [
                    {
                      "isin": "PLBSK0000017",
                      "price": 321
                    }
                  ]
                }
        """;


        stubFor(WireMock.get(("/prices")).willReturn(okJson(response)));

        var entity = webTestClient.get().uri("/GPW/prices")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<WrappedResponseDto<PriceDto>>() {})
                .returnResult()
                .getResponseBody();
    }
}

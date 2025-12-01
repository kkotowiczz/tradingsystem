package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.dto.WrappedResponseDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
@TestPropertySource(locations = "classpath:test.properties")
class StockTickersTests extends AbstractIntegrationTest {
    private WireMockServer wireMockServer;
    private int port = 3001;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(port));
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
    }

    @Test
    void getTickers() {
        var response = """
        {
            "results": [
                {    
                    "name": "ING Bank Śląski",
                    "ticker": "INGBSK",
                    "isin": "PLBSK0000017",
                    "tradeCurrency": "PLN",
                    "mic": "XWAR"
                }
            ]
        }
        """;


        stubFor(WireMock.get(("/tickers"))
                .willReturn(okJson(response)));

        var entity = webTestClient.get().uri("/GPW/tickers")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(WrappedResponseDto.class)
                .returnResult()
                .getResponseBody();
    }
}
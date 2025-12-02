package com.github.kkotowiczz.tradingsystem.stock.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderEventProducer {
    private static final String TOPIC = "orders";
    private final KafkaTemplate<BigInteger, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<BigInteger, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<BigInteger, OrderCreatedEvent>> sendOrderCreatedEvent(OrderCreatedEvent event) {
        return kafkaTemplate.send(TOPIC, event);
    }
}

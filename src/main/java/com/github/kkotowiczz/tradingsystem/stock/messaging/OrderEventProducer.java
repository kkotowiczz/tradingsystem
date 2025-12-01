package com.github.kkotowiczz.tradingsystem.stock.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class OrderEventProducer {
    private static final String TOPIC = "orders";
    private final KafkaTemplate<BigInteger, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<BigInteger, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}

package com.github.kkotowiczz.tradingsystem.stock.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedConsumer {
    private static final String TOPIC = "orders";
    @KafkaListener(topics = TOPIC, groupId = "order_group")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

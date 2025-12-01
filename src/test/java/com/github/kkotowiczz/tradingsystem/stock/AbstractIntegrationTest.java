package com.github.kkotowiczz.tradingsystem.stock;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;


public class AbstractIntegrationTest {
    static GenericContainer<?> postgres = new GenericContainer<>(DockerImageName.parse("postgres:16-alpine"));
    static GenericContainer<?> kafka = new GenericContainer<>(DockerImageName.parse("apache/kafka:4.1.1"));
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"));

    static {
        redis.setPortBindings(List.of("5555:6379"));
        redis.start();
        postgres.setPortBindings(List.of("4444:5432"));
        postgres.addEnv("POSTGRES_PASSWORD", "PASSWORD");
        postgres.start();
        kafka.start();
    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

    }

}

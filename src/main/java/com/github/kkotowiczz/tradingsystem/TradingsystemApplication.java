package com.github.kkotowiczz.tradingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@EnableCaching
@EnableScheduling
public class TradingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingsystemApplication.class, args);
	}

    // TODO: CREATE VALIDATORS FOR ORDER DTO https://www.baeldung.com/spring-mvc-custom-validator
}

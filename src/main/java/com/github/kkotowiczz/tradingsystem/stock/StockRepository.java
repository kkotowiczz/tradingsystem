package com.github.kkotowiczz.tradingsystem.stock;

import com.github.kkotowiczz.tradingsystem.stock.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;


public interface StockRepository extends ReactiveCrudRepository<Order, BigInteger> {
}

package com.github.kkotowiczz.tradingsystem.stock.mocks;


public class JwtProviderMock {
    public static Long getAccountId(String jwt) {
        return Long.valueOf(123L);
    }
}

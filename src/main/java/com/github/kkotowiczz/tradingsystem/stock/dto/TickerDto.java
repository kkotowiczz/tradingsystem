package com.github.kkotowiczz.tradingsystem.stock.dto;

import com.github.kkotowiczz.tradingsystem.stock.domain.Currency;
import com.github.kkotowiczz.tradingsystem.stock.domain.MarketIdentifierCode;

public class TickerDto {
    private String name;
    private String ticker;
    private String isin;
    private Currency tradeCurrency;
    private MarketIdentifierCode mic;

    public TickerDto() {}

    public TickerDto(String name, String ticker, String isin, Currency tradeCurrency, MarketIdentifierCode mic) {
        this.name = name;
        this.ticker = ticker;
        this.isin = isin;
        this.tradeCurrency = tradeCurrency;
        this.mic = mic;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Currency getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(Currency tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public MarketIdentifierCode getMic() {
        return mic;
    }

    public void setMic(MarketIdentifierCode mic) {
        this.mic = mic;
    }

    @Override
    public String toString() {
        return "TickerDto{" +
                "name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", isin='" + isin + '\'' +
                ", tradeCurrency=" + tradeCurrency +
                ", mic=" + mic +
                '}';
    }
}

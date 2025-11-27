package com.github.kkotowiczz.tradingsystem.stock.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PriceDto implements Externalizable {
    private String isin;
    private Long price;

    private static final long serialVersionUID = -1070509815898774406L;

    public PriceDto() {}

    public PriceDto(String isin, Long price) {
        this.isin = isin;
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(isin);
        objectOutput.writeLong(price);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        isin = objectInput.readUTF();
        price = objectInput.readLong();
    }
}

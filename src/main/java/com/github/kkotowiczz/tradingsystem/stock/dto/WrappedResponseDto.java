package com.github.kkotowiczz.tradingsystem.stock.dto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class WrappedResponseDto<T> implements Externalizable {
    private List<T> results;

    public WrappedResponseDto() {}

    public WrappedResponseDto(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "WrappedResponseDto{" +
                "results=" + results +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(results);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        results = (List<T>) objectInput.readObject();
    }
}

package com.christopherrons.refdataservice.historicalprices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalPriceResponse implements Serializable {

    @JsonProperty
    private String symbol;

    @JsonProperty
    private int dataGranularity;

    @JsonProperty
    private List<Long> timestamp;

    @JsonProperty
    private List<Double> close;

    public boolean isEmpty() {
        return close.size() == 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDataGranularity() {
        return dataGranularity;
    }

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public List<Double> getClose() {
        return close;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDataGranularity(int dataGranularity) {
        this.dataGranularity = dataGranularity;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public void setClose(List<Double> close) {
        this.close = close;
    }

    public List<Double> createReturns() {
        List<Double> returns = new ArrayList<>();
        for (int i = 0; i < close.size() - 1; i++) {
            double dailyReturnAbsolut = close.get(i + 1) - close.get(i);
            double dailyReturnRelative = dailyReturnAbsolut / close.get(i);
            returns.add(dailyReturnRelative);
        }
        return returns;
    }
}

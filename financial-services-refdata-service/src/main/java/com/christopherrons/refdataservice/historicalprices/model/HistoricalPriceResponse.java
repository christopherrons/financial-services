package com.christopherrons.refdataservice.historicalprices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalPriceResponse implements Serializable {

    @JsonProperty
    private String symbol;

    @JsonProperty
    private int dataGranularity;

    @JsonProperty
    private Integer[] timestamp;

    @JsonProperty
    private Double[] close;

    public boolean isEmpty() {
        return close.length == 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDataGranularity() {
        return dataGranularity;
    }

    public Integer[] getTimestamp() {
        return timestamp;
    }

    public Double[] getClose() {
        return close;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDataGranularity(int dataGranularity) {
        this.dataGranularity = dataGranularity;
    }

    public void setTimestamp(Integer[] timestamp) {
        this.timestamp = timestamp;
    }

    public void setClose(Double[] close) {
        this.close = close;
    }
}

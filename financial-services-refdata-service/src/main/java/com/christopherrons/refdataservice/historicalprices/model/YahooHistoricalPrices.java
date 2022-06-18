package com.christopherrons.refdataservice.historicalprices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooHistoricalPrices implements Serializable {

    @JsonProperty("BTC-USD")
    private HistoricalPriceResponse BTC_USD;

    @JsonProperty("XRP-USD")
    private HistoricalPriceResponse XRP_USD;


    public HistoricalPriceResponse getResponse(final String symbol) {
        return switch (symbol) {
            case "BTC-USD" -> BTC_USD;
            case "XRP-USD" -> XRP_USD;
            default -> null;
        };
    }


    public List<HistoricalPriceResponse> getResponses() {
        return List.of(BTC_USD, XRP_USD);
    }

}

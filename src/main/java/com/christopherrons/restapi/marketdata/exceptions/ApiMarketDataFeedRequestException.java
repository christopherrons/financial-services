package com.christopherrons.restapi.marketdata.exceptions;

import java.util.List;

public class ApiMarketDataFeedRequestException extends ApiRequestException{
    public ApiMarketDataFeedRequestException(String unavailableDataFeed, List<String> availableDataFeed) {
        super(String.format("Data feed %s not available. Available data feed: %s.", unavailableDataFeed, availableDataFeed));
    }
}

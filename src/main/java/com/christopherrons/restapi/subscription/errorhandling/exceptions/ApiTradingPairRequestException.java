package com.christopherrons.restapi.subscription.errorhandling.exceptions;

import java.util.List;

public class ApiTradingPairRequestException extends ApiRequestException {

    public ApiTradingPairRequestException(String unavailableTradinPair, List<String> availableTradingPairs) {
        super(String.format("Trading Pair %s not available. Available trading pairs are: %s", unavailableTradinPair, availableTradingPairs));
    }
}

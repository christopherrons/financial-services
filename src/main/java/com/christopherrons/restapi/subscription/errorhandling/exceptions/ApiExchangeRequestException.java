package com.christopherrons.restapi.subscription.errorhandling.exceptions;

import java.util.List;

public class ApiExchangeRequestException extends ApiRequestException{
    public ApiExchangeRequestException(String unavailableExchange, List<String> availableExchanges) {
        super(String.format("Exchange %s not available. Available exchanges: %s.", unavailableExchange, availableExchanges));
    }
}

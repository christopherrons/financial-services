package com.christopherrons.restapi.marketdata.exceptions;

import java.util.List;

public class ApiChannelRequestException extends ApiRequestException {

    public ApiChannelRequestException(String unavailableChannel, List<String> availableChannel) {
        super(String.format("Channel %s not available. Available channels are: %s", unavailableChannel, availableChannel));
    }
}

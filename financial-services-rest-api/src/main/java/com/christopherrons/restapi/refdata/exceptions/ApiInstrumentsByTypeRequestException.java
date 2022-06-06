package com.christopherrons.restapi.refdata.exceptions;

import com.christopherrons.restapi.marketdata.exceptions.ApiRequestException;

import java.util.List;

public class ApiInstrumentsByTypeRequestException extends ApiRequestException {

    public ApiInstrumentsByTypeRequestException(String unavailableInstrumentType, List<String> availableInstrumentType) {
        super(String.format("Instrument type %s not available. Available Instrument type are: %s", unavailableInstrumentType, availableInstrumentType));
    }
}


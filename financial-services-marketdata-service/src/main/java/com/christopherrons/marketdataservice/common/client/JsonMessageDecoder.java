package com.christopherrons.marketdataservice.common.client;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.model.marketdata.InvalidEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMessageDecoder {
    private final Class<? extends MarketDataEvent> classToBeDecoded;

    public JsonMessageDecoder(Class<? extends MarketDataEvent> classToBeDecoded) {
        this.classToBeDecoded = classToBeDecoded;
    }
    public MarketDataEvent decodeMessage(final String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, classToBeDecoded);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new InvalidEvent();
    }

}

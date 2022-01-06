package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.common.event.InvalidEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMessageDecoder {

    private final Class<? extends ExchangeEvent> classToBeDecoded;

    public JsonMessageDecoder(Class<? extends ExchangeEvent> classToBeDecoded) {
        this.classToBeDecoded = classToBeDecoded;
    }

    public ExchangeEvent decodeMessage(final String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, classToBeDecoded);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new InvalidEvent();
    }

}

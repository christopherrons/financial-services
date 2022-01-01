package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.common.event.Event;
import com.christopherrons.shadoworderbook.exchange.common.event.InvalidEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDecoder<T extends Event> {

    private final Class<T> classToBeDecoded;

    public MessageDecoder(Class<T> classToBeDecoded) {
        this.classToBeDecoded = classToBeDecoded;
    }

    public ExchangeEvent decodeToExchangeEvent(final String message) {
        ExchangeEvent event = decodeMessage(message);
        return event != null ? event : new InvalidEvent();
    }

    public T decodeMessage(final String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, classToBeDecoded);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

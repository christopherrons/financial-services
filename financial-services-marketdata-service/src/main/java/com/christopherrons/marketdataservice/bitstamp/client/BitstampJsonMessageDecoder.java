package com.christopherrons.marketdataservice.bitstamp.client;

import com.christopherrons.marketdataservice.bitstamp.model.BitstampEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BitstampJsonMessageDecoder {

    private final Class<? extends BitstampEvent> classToBeDecoded;

    public BitstampJsonMessageDecoder(Class<? extends BitstampEvent> classToBeDecoded) {
        this.classToBeDecoded = classToBeDecoded;
    }

    public BitstampEvent decodeMessage(final String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, classToBeDecoded);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

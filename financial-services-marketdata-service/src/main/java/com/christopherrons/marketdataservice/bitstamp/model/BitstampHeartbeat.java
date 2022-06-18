package com.christopherrons.marketdataservice.bitstamp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BitstampHeartbeat {


    private boolean isSuccessful;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampHeartbeat(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        this.isSuccessful = data.get("status").equals("success");
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}

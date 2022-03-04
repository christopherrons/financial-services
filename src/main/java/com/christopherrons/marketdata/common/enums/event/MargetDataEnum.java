package com.christopherrons.marketdata.common.enums.event;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public enum MargetDataEnum {
    INVALID_EXCHANGE("none", "none"),
    BITSTAMP("bitstamp", "wss://ws.bitstamp.net");

    private final String name;
    private final String uri;

    MargetDataEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public static MargetDataEnum fromName(String name) {
        switch (name.toLowerCase()) {
            case "bitstamp":
                return BITSTAMP;
            default:
                return INVALID_EXCHANGE;
        }
    }

    public String getName() {
        return name;
    }

    public static List<String> getExchangeNames() {
        List<String> exchangeNames = new ArrayList<>();
        for (MargetDataEnum margetDataEnum : MargetDataEnum.values()) {
            if (margetDataEnum.equals(MargetDataEnum.INVALID_EXCHANGE)) {
                continue;
            }
            exchangeNames.add(margetDataEnum.getName());
        }
        return exchangeNames;
    }

    public URI getUri() {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUriString() {
        return uri;
    }
}

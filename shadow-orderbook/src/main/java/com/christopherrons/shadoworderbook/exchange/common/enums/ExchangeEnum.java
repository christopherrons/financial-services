package com.christopherrons.shadoworderbook.exchange.common.enums;

import java.net.URI;
import java.net.URISyntaxException;

public enum ExchangeEnum {
    INVALID_EXCHANGE("none", "none"),
    BITSTAMP("bitstamp", "wss://ws.bitstamp.net");

    private final String name;
    private final String uri;

    ExchangeEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public ExchangeEnum fromName(String name) {
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

    public URI getUri() throws URISyntaxException {
        return new URI(uri);
    }

    public String getUriString() {
        return uri;
    }
}

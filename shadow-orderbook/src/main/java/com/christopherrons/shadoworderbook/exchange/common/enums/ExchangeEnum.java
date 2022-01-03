package com.christopherrons.shadoworderbook.exchange.common.enums;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public enum ExchangeEnum {
    INVALID_EXCHANGE("none", "none"),
    BITSTAMP("bitstamp", "wss://ws.bitstamp.net");

    private final String name;
    private final String uri;

    ExchangeEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public static ExchangeEnum fromName(String name) {
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
        for (ExchangeEnum exchangeEnum : ExchangeEnum.values()) {
            if (exchangeEnum.equals(ExchangeEnum.INVALID_EXCHANGE)) {
                continue;
            }
            exchangeNames.add(exchangeEnum.getName());
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

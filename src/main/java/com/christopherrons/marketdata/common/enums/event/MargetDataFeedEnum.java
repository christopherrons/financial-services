package com.christopherrons.marketdata.common.enums.event;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public enum MargetDataFeedEnum {
    INVALID_DATA_FEED("none", "none"),
    BITSTAMP("bitstamp", "wss://ws.bitstamp.net");

    private final String name;
    private final String uri;

    MargetDataFeedEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public static MargetDataFeedEnum fromName(String name) {
        switch (name.toLowerCase()) {
            case "bitstamp":
                return BITSTAMP;
            default:
                return INVALID_DATA_FEED;
        }
    }

    public static List<String> getDataFeedNames() {
        List<String> dataFeedNames = new ArrayList<>();
        for (MargetDataFeedEnum margetDataFeedEnum : MargetDataFeedEnum.values()) {
            if (margetDataFeedEnum.equals(MargetDataFeedEnum.INVALID_DATA_FEED)) {
                continue;
            }
            dataFeedNames.add(margetDataFeedEnum.getName());
        }
        return dataFeedNames;
    }

    public String getName() {
        return name;
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

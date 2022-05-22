package com.christopherrons.marketdata.common.enums.event;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum MarketDataFeedEnum {
    INVALID_DATA_FEED("none", "none"),
    BITSTAMP("bitstamp", "wss://ws.bitstamp.net");

    private static final Map<String, MarketDataFeedEnum> VALUES_BY_IDENTIFIER =
            stream(MarketDataFeedEnum.values()).collect(toMap(MarketDataFeedEnum::getName, identity()));
    private final String name;
    private final String uri;

    MarketDataFeedEnum(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public static MarketDataFeedEnum fromName(String name) {
        return VALUES_BY_IDENTIFIER.getOrDefault(name.toLowerCase(), INVALID_DATA_FEED);
    }

    public static List<String> getDataFeedNames() {
        List<String> dataFeedNames = new ArrayList<>();
        for (MarketDataFeedEnum marketDataFeedEnum : MarketDataFeedEnum.values()) {
            if (marketDataFeedEnum.equals(MarketDataFeedEnum.INVALID_DATA_FEED)) {
                continue;
            }
            dataFeedNames.add(marketDataFeedEnum.getName());
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

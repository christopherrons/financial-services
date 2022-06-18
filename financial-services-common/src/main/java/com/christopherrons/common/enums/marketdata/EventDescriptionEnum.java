package com.christopherrons.common.enums.marketdata;

import java.util.Arrays;
import java.util.List;

public enum EventDescriptionEnum {
    INVALID_EVENT_DESCRIPTION(MarketDataFeedEnum.INVALID_DATA_FEED, "none"),
    ORDER_CREATED(MarketDataFeedEnum.BITSTAMP, "order_created"),
    ORDER_DELETED(MarketDataFeedEnum.BITSTAMP, "order_deleted"),
    ORDER_UPDATED(MarketDataFeedEnum.BITSTAMP, "order_changed"),
    TRADE(MarketDataFeedEnum.BITSTAMP, "trade"),
    SUBSCRIPTION_SUCCEEDED(MarketDataFeedEnum.BITSTAMP, "bts:subscription_succeeded"),
    FORCED_RECONNECT(MarketDataFeedEnum.BITSTAMP, "bts:request_reconnect"),
    HEART_BEAT(MarketDataFeedEnum.BITSTAMP, "bts:heartbeat");

    private final MarketDataFeedEnum marketDataFeedEnum;
    private final String eventDescription;

    EventDescriptionEnum(MarketDataFeedEnum marketDataFeedEnum, String eventDescription) {
        this.marketDataFeedEnum = marketDataFeedEnum;
        this.eventDescription = eventDescription;
    }

    public static EventDescriptionEnum inferEventDescriptionEnum(final String eventDescription, final MarketDataFeedEnum marketDataFeedEnum) {
        for (EventDescriptionEnum eventDescriptionEnum : getAvailableExchangeEventDescriptionEnums(marketDataFeedEnum)) {
            if (eventDescription.contains(eventDescriptionEnum.getEventDescription())) {
                return eventDescriptionEnum;
            }
        }
        return INVALID_EVENT_DESCRIPTION;
    }

    public static List<String> getAvailableExchangeEventDescriptions(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .map(EventDescriptionEnum::getEventDescription)
                .toList();
    }

    public static List<EventDescriptionEnum> getAvailableExchangeEventDescriptionEnums(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .toList();
    }

    public MarketDataFeedEnum getExchangeEnum() {
        return marketDataFeedEnum;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}

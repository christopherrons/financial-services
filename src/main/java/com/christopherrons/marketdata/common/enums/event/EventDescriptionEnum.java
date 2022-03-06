package com.christopherrons.marketdata.common.enums.event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EventDescriptionEnum {
    INVALID_EVENT_DESCRIPTION(MargetDataFeedEnum.INVALID_DATA_FEED, "none"),
    ORDER_CREATED(MargetDataFeedEnum.BITSTAMP, "order_created"),
    ORDER_DELETED(MargetDataFeedEnum.BITSTAMP, "order_deleted"),
    ORDER_UPDATED(MargetDataFeedEnum.BITSTAMP, "order_changed"),
    TRADE(MargetDataFeedEnum.BITSTAMP, "trade"),
    SUBSCRIPTION_SUCCEEDED(MargetDataFeedEnum.BITSTAMP, "bts:subscription_succeeded"),
    FORCED_RECONNECT(MargetDataFeedEnum.BITSTAMP, "bts:request_reconnect");

    private final MargetDataFeedEnum margetDataFeedEnum;
    private final String eventDescription;

    EventDescriptionEnum(MargetDataFeedEnum margetDataFeedEnum, String eventDescription) {
        this.margetDataFeedEnum = margetDataFeedEnum;
        this.eventDescription = eventDescription;
    }

    public static EventDescriptionEnum inferEventDescriptionEnum(final String eventDescription, final MargetDataFeedEnum margetDataFeedEnum) {
        for (EventDescriptionEnum eventDescriptionEnum : getAvailableExchangeEventDescriptionEnums(margetDataFeedEnum)) {
            if (eventDescription.contains(eventDescriptionEnum.getEventDescription())) {
                return eventDescriptionEnum;
            }
        }
        return INVALID_EVENT_DESCRIPTION;
    }

    public static List<String> getAvailableExchangeEventDescriptions(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .map(EventDescriptionEnum::getEventDescription)
                .collect(Collectors.toList());
    }

    public static List<EventDescriptionEnum> getAvailableExchangeEventDescriptionEnums(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .collect(Collectors.toList());
    }

    public MargetDataFeedEnum getExchangeEnum() {
        return margetDataFeedEnum;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}

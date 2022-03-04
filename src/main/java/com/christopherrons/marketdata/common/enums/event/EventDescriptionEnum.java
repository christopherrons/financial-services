package com.christopherrons.marketdata.common.enums.event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EventDescriptionEnum {
    INVALID_EVENT_DESCRIPTION(MargetDataEnum.INVALID_EXCHANGE, "none"),
    ORDER_CREATED(MargetDataEnum.BITSTAMP, "order_created"),
    ORDER_DELETED(MargetDataEnum.BITSTAMP, "order_deleted"),
    ORDER_UPDATED(MargetDataEnum.BITSTAMP, "order_changed"),
    TRADE(MargetDataEnum.BITSTAMP, "trade"),
    SUBSCRIPTION_SUCCEEDED(MargetDataEnum.BITSTAMP, "bts:subscription_succeeded"),
    FORCED_RECONNECT(MargetDataEnum.BITSTAMP, "bts:request_reconnect");

    private final MargetDataEnum margetDataEnum;
    private final String eventDescription;

    EventDescriptionEnum(MargetDataEnum margetDataEnum, String eventDescription) {
        this.margetDataEnum = margetDataEnum;
        this.eventDescription = eventDescription;
    }

    public MargetDataEnum getExchangeEnum() {
        return margetDataEnum;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public static EventDescriptionEnum inferEventDescriptionEnum(final String eventDescription, final MargetDataEnum margetDataEnum) {
        for (EventDescriptionEnum eventDescriptionEnum : getAvailableExchangeEventDescriptionEnums(margetDataEnum)) {
            if (eventDescription.contains(eventDescriptionEnum.getEventDescription())) {
                return eventDescriptionEnum;
            }
        }
        return INVALID_EVENT_DESCRIPTION;
    }

    public static List<String> getAvailableExchangeEventDescriptions(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(margetDataEnum))
                .map(EventDescriptionEnum::getEventDescription)
                .collect(Collectors.toList());
    }

    public static List<EventDescriptionEnum> getAvailableExchangeEventDescriptionEnums(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(margetDataEnum))
                .collect(Collectors.toList());
    }
}

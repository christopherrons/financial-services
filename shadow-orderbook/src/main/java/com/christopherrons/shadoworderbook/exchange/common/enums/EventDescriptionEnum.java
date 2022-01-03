package com.christopherrons.shadoworderbook.exchange.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EventDescriptionEnum {
    INVALID_EVENT_DESCRIPTION(ExchangeEnum.INVALID_EXCHANGE, "none"),
    ORDER_CREATED(ExchangeEnum.BITSTAMP, "order_created"),
    ORDER_DELETED(ExchangeEnum.BITSTAMP, "order_deleted"),
    ORDER_UPDATED(ExchangeEnum.BITSTAMP, "order_changed"),
    TRADE(ExchangeEnum.BITSTAMP, "trade"),
    SUBSCRIPTION_SUCCEEDED(ExchangeEnum.BITSTAMP, "bts:subscription_succeeded"),
    FORCED_RECONNECT(ExchangeEnum.BITSTAMP, "bts:request_reconnect");

    private final ExchangeEnum exchangeEnum;
    private final String eventDescription;

    EventDescriptionEnum(ExchangeEnum exchangeEnum, String eventDescription) {
        this.exchangeEnum = exchangeEnum;
        this.eventDescription = eventDescription;
    }

    public ExchangeEnum getExchangeEnum() {
        return exchangeEnum;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public static EventDescriptionEnum inferEventDescriptionEnum(final String eventDescription, final ExchangeEnum exchangeEnum) {
        for (EventDescriptionEnum eventDescriptionEnum : getAvailableExchangeEventDescriptionEnums(exchangeEnum)) {
            if (eventDescription.contains(eventDescriptionEnum.getEventDescription())) {
                return eventDescriptionEnum;
            }
        }
        return INVALID_EVENT_DESCRIPTION;
    }

    public static List<String> getAvailableExchangeEventDescriptions(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(exchangeEnum))
                .map(EventDescriptionEnum::getEventDescription)
                .collect(Collectors.toList());
    }

    public static List<EventDescriptionEnum> getAvailableExchangeEventDescriptionEnums(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(EventDescriptionEnum.values())
                .filter(eventDescriptionEnum -> !eventDescriptionEnum.equals(EventDescriptionEnum.INVALID_EVENT_DESCRIPTION))
                .filter(eventDescriptionEnum -> eventDescriptionEnum.getExchangeEnum().equals(exchangeEnum))
                .collect(Collectors.toList());
    }
}

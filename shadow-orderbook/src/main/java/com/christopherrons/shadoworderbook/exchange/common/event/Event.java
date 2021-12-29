package com.christopherrons.shadoworderbook.exchange.common.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.api.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.api.enums.EventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

public class Event implements ExchangeEvent {

    private final ExchangeEnum exchangeEnum;
    private final EventDescriptionEnum eventDescriptionEnum;
    private final ChannelEnum channelEnum;
    private final EventTypeEnum eventTypeEnum;

    public Event(ExchangeEnum exchangeEnum, EventDescriptionEnum eventDescriptionEnum, ChannelEnum channelEnum, EventTypeEnum eventTypeEnum) {
        this.exchangeEnum = exchangeEnum;
        this.eventDescriptionEnum = eventDescriptionEnum;
        this.channelEnum = channelEnum;
        this.eventTypeEnum = eventTypeEnum;
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    @Override
    public ChannelEnum getChannelEnum() {
        return channelEnum;
    }

    @Override
    public ExchangeEnum getExchangeEnum() {
        return exchangeEnum;
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return eventDescriptionEnum;
    }
}

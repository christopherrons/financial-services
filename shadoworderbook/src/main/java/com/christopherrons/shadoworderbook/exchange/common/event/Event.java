package com.christopherrons.shadoworderbook.exchange.common.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;

public class Event implements ExchangeEvent {

    private final ExchangeEnum exchangeEnum;
    private final EventDescriptionEnum eventDescriptionEnum;
    private final ChannelEnum channelEnum;
    private final EventTypeEnum eventTypeEnum;
    private final TradingPairEnum tradingPairEnum;

    public Event(ExchangeEnum exchangeEnum, String eventDescription, String channel, EventTypeEnum eventTypeEnum) {
        this.exchangeEnum = exchangeEnum;
        this.eventDescriptionEnum = EventDescriptionEnum.inferEventDescriptionEnum(eventDescription, exchangeEnum);
        this.channelEnum = ChannelEnum.inferChannelEnum(channel, exchangeEnum);
        this.eventTypeEnum = eventTypeEnum;
        this.tradingPairEnum = TradingPairEnum.inferTradingPairEnum(channel, exchangeEnum);
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

    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    @Override
    public String toString() {
        return "Event{" +
                "exchangeEnum=" + exchangeEnum +
                ", eventDescriptionEnum=" + eventDescriptionEnum +
                ", channelEnum=" + channelEnum +
                ", eventTypeEnum=" + eventTypeEnum +
                ", tradingPairEnum=" + tradingPairEnum +
                '}';
    }
}

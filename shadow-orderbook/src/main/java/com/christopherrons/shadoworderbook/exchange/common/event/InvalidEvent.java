package com.christopherrons.shadoworderbook.exchange.common.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.common.enums.*;

public class InvalidEvent implements ExchangeEvent {

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return null;
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return EventTypeEnum.INVALID_EVENT_TYPE;
    }

    @Override
    public ChannelEnum getChannelEnum() {
        return null;
    }

    @Override
    public ExchangeEnum getExchangeEnum() {
        return ExchangeEnum.INVALID_EXCHANGE;
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return null;
    }
}

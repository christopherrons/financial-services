package com.christopherrons.shadoworderbook.exchange.common.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.api.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.api.enums.EventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

public class InvalidEvent implements ExchangeEvent {

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

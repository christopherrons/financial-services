package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.*;

public interface ExchangeEvent {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    ChannelEnum getChannelEnum();

    ExchangeEnum getExchangeEnum();

    EventDescriptionEnum getEventDescriptionEnum();

}

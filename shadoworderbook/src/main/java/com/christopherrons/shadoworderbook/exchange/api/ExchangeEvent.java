package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;

public interface ExchangeEvent {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    ChannelEnum getChannelEnum();

    ExchangeEnum getExchangeEnum();

    EventDescriptionEnum getEventDescriptionEnum();

}

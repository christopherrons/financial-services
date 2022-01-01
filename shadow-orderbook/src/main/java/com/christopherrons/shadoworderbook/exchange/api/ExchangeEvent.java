package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.api.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.api.enums.EventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

public interface ExchangeEvent {
    EventTypeEnum getEventTypeEnum();

    ChannelEnum getChannelEnum();

    ExchangeEnum getExchangeEnum();

    EventDescriptionEnum getEventDescriptionEnum();

}

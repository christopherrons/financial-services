package com.christopherrons.marketdata.api;

import com.christopherrons.common.api.HasTimeStamp;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;

public interface MarketDataEvent extends HasTimeStamp {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    ChannelEnum getChannelEnum();

    MargetDataFeedEnum getMarketDataEnum();

    EventDescriptionEnum getEventDescriptionEnum();

    String getOrderbookId();

    Instrument getInstrument();

}

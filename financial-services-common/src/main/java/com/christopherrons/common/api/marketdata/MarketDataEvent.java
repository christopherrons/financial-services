package com.christopherrons.common.api.marketdata;

import com.christopherrons.common.api.HasTimeStamp;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.*;

public interface MarketDataEvent extends HasTimeStamp {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    MarketDataFeedEnum getMarketDataEnum();

    EventDescriptionEnum getEventDescriptionEnum();

    String getOrderbookId();

    Instrument getInstrument();

}

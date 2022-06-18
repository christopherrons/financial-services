package com.christopherrons.common.api.marketdata;

import com.christopherrons.common.api.HasTimeStamp;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.EventTypeEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

public interface MarketDataEvent extends HasTimeStamp {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    MarketDataFeedEnum getMarketDataEnum();

    EventDescriptionEnum getEventDescriptionEnum();

    String getOrderbookId();

    Instrument getInstrument();

}

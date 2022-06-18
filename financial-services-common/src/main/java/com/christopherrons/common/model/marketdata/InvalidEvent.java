package com.christopherrons.common.model.marketdata;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.EventTypeEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.model.refdata.InvalidInstrument;

public class InvalidEvent implements MarketDataEvent {

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return null;
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return EventTypeEnum.INVALID_EVENT_TYPE;
    }

    @Override
    public MarketDataFeedEnum getMarketDataEnum() {
        return MarketDataFeedEnum.INVALID_DATA_FEED;
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return null;
    }

    @Override
    public String getOrderbookId() {
        return null;
    }

    @Override
    public Instrument getInstrument() {
        return new InvalidInstrument();
    }

    @Override
    public long getTimeStampMs() {
        return 0;
    }
}

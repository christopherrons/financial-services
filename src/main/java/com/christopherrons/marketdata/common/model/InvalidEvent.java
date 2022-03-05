package com.christopherrons.marketdata.common.model;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.model.instruments.InvalidInstrument;
import com.christopherrons.refdata.model.participant.Participant;

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
    public ChannelEnum getChannelEnum() {
        return null;
    }

    @Override
    public MargetDataFeedEnum getMarketDataEnum() {
        return MargetDataFeedEnum.INVALID_DATA_FEED;
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
    public Participant getParticipant() {
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

package com.christopherrons.common.model.marketdata;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.EventTypeEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

public class EventData implements MarketDataEvent {
    private final MarketDataFeedEnum marketDataFeedEnum;
    private final EventDescriptionEnum eventDescriptionEnum;
    private final EventTypeEnum eventTypeEnum;
    private final long timeStampInMs;
    private final String orderbookId;
    private final Instrument instrument;

    public EventData(MarketDataFeedEnum marketDataFeedEnum,
                     EventDescriptionEnum eventDescriptionEnum,
                     EventTypeEnum eventTypeEnum,
                     long timeStampInMs,
                     Instrument instrument) {
        this.marketDataFeedEnum = marketDataFeedEnum;
        this.eventDescriptionEnum = eventDescriptionEnum;
        this.eventTypeEnum = eventTypeEnum;
        this.orderbookId = String.format("%s-%s", marketDataFeedEnum.getName(), instrument.getInstrumentId());
        this.timeStampInMs = timeStampInMs;
        this.instrument = instrument;
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    @Override
    public MarketDataFeedEnum getMarketDataEnum() {
        return marketDataFeedEnum;
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return eventDescriptionEnum;
    }

    @Override
    public String getOrderbookId() {
        return orderbookId;
    }

    public TradingPairEnum getTradingPairEnum() {
        return instrument.getTradingPairEnum();
    }

    @Override
    public long getTimeStampMs() {
        return timeStampInMs;
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public String toString() {
        return "EventData{" +
                "margetDataEnum=" + marketDataFeedEnum +
                ", eventDescriptionEnum=" + eventDescriptionEnum +
                ", eventTypeEnum=" + eventTypeEnum +
                ", timeStampInMs=" + timeStampInMs +
                ", orderbookId='" + orderbookId + '\'' +
                ", instrument=" + instrument +
                '}';
    }
}

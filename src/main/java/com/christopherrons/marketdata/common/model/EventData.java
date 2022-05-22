package com.christopherrons.marketdata.common.model;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;

public class EventData implements MarketDataEvent {
    private final MarketDataFeedEnum marketDataFeedEnum;
    private final EventDescriptionEnum eventDescriptionEnum;
    private final ChannelEnum channelEnum;
    private final EventTypeEnum eventTypeEnum;
    private final long timeStampInMs;
    private final String orderbookId;
    private final Instrument instrument;

    public EventData(MarketDataFeedEnum marketDataFeedEnum,
                     EventDescriptionEnum eventDescriptionEnum,
                     ChannelEnum channelEnum,
                     EventTypeEnum eventTypeEnum,
                     long timeStampInMs,
                     Instrument instrument) {
        this.marketDataFeedEnum = marketDataFeedEnum;
        this.eventDescriptionEnum = eventDescriptionEnum;
        this.channelEnum = channelEnum;
        this.eventTypeEnum = eventTypeEnum;
        this.orderbookId = String.format("%s-%s", marketDataFeedEnum.getName(), instrument.getTradingPairEnum().getName()); //TODO: Not correct must be Feed-TradingPair-Type
        this.timeStampInMs = timeStampInMs;
        this.instrument = instrument;
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    @Override
    public ChannelEnum getChannelEnum() {
        return channelEnum;
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
                ", channelEnum=" + channelEnum +
                ", eventTypeEnum=" + eventTypeEnum +
                ", timeStampInMs=" + timeStampInMs +
                ", orderbookId='" + orderbookId + '\'' +
                ", instrument=" + instrument +
                '}';
    }
}

package com.christopherrons.marketdata.common.model;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.model.participant.Participant;

public class EventData implements MarketDataEvent {

    private final MargetDataFeedEnum margetDataFeedEnum;
    private final EventDescriptionEnum eventDescriptionEnum;
    private final ChannelEnum channelEnum;
    private final EventTypeEnum eventTypeEnum;
    private final TradingPairEnum tradingPairEnum;
    private final long timeStampInMs;
    private final Participant participant;
    private final String orderbookId;
    private final Instrument instrument;

    public EventData(MargetDataFeedEnum margetDataFeedEnum,
                     String eventDescription,
                     String channel,
                     EventTypeEnum eventTypeEnum,
                     long timeStampInMs,
                     Participant participant,
                     InstrumentTypeEnum instrumentedType) {
        this.margetDataFeedEnum = margetDataFeedEnum;
        this.eventDescriptionEnum = EventDescriptionEnum.inferEventDescriptionEnum(eventDescription, margetDataFeedEnum);
        this.channelEnum = ChannelEnum.inferChannelEnum(channel, margetDataFeedEnum);
        this.eventTypeEnum = eventTypeEnum;
        this.tradingPairEnum = TradingPairEnum.inferTradingPairEnum(channel, margetDataFeedEnum);
        this.orderbookId = String.format("%s-%s", margetDataFeedEnum.getName(), tradingPairEnum.getName());
        this.timeStampInMs = timeStampInMs;
        this.participant = participant;
        this.instrument = Instrument.createInstrument(instrumentedType, tradingPairEnum.getName());
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
    public MargetDataFeedEnum getMarketDataEnum() {
        return margetDataFeedEnum;
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
        return tradingPairEnum;
    }

    @Override
    public long getTimeStampMs() {
        return timeStampInMs;
    }

    @Override
    public Participant getParticipant() {
        return participant;
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public String toString() {
        return "EventData{" +
                "margetDataEnum=" + margetDataFeedEnum +
                ", eventDescriptionEnum=" + eventDescriptionEnum +
                ", channelEnum=" + channelEnum +
                ", eventTypeEnum=" + eventTypeEnum +
                ", tradingPairEnum=" + tradingPairEnum +
                ", timeStampInMs=" + timeStampInMs +
                ", participant=" + participant +
                ", orderbookId='" + orderbookId + '\'' +
                ", instrument=" + instrument +
                '}';
    }
}

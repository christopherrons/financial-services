package com.christopherrons.marketdata.common.model;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.participant.model.User;

public class Trade implements MarketDataTrade {
    private final Participant askParticipant;
    private final Participant bidParticipant;
    private final EventData eventData;
    private long tradeId;
    private long buyOrderId;
    private long sellOrderId;
    private boolean isBidSideAggressor;
    private double volume;
    private double price;

    public Trade(MarketDataFeedEnum marketDataFeedEnum,
                 Participant bidParticipant,
                 Participant askParticipant,
                 long tradeId,
                 long buyOrderId,
                 long sellOrderId,
                 boolean isBidSideAggressor,
                 double volume,
                 double price,
                 long timeStampInMs,
                 Instrument instrument,
                 EventDescriptionEnum eventDescriptionEnum,
                 ChannelEnum channelEnum,
                 EventTypeEnum eventTypeEnum) {

        this.askParticipant = askParticipant;
        this.bidParticipant = bidParticipant;
        this.tradeId = tradeId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.isBidSideAggressor = isBidSideAggressor;
        this.volume = volume;
        this.price = price;

        this.eventData = new EventData(
                marketDataFeedEnum,
                eventDescriptionEnum,
                channelEnum,
                eventTypeEnum,
                timeStampInMs,
                instrument
        );
    }

    @Override
    public long getTradeId() {
        return tradeId;
    }

    @Override
    public long getBuyOrderId() {
        return buyOrderId;
    }

    @Override
    public long getSellOrderId() {
        return sellOrderId;
    }

    @Override
    public boolean isBidSideAggressor() {
        return isBidSideAggressor;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public long getTimeStampMs() {
        return eventData.getTimeStampMs();
    }

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return eventData.getTradingPairEnum();
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return eventData.getEventTypeEnum();
    }

    @Override
    public ChannelEnum getChannelEnum() {
        return eventData.getChannelEnum();
    }

    @Override
    public MarketDataFeedEnum getMarketDataEnum() {
        return eventData.getMarketDataEnum();
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return eventData.getEventDescriptionEnum();
    }

    @Override
    public String getOrderbookId() {
        return eventData.getOrderbookId();
    }

    @Override
    public Instrument getInstrument() {
        return eventData.getInstrument();
    }

    @Override
    public User tradeAggressorUser() {
        return isBidSideAggressor ? bidParticipant.getUser() : askParticipant.getUser();
    }

    @Override
    public Participant getAskParticipant() {
        return askParticipant;
    }

    @Override
    public Participant getBidParticipant() {
        return bidParticipant;
    }

    @Override
    public String toString() {
        return eventData + "-" +"Trade{" +
                "askParticipant=" + askParticipant +
                ", bidParticipant=" + bidParticipant +
                ", eventData=" + eventData +
                ", tradeId=" + tradeId +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", isBidSideAggressor=" + isBidSideAggressor +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}

package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

public class ApiTradeRequest {

    private MarketDataFeedEnum marketDataFeed;
    private TradingPairEnum tradingPair;
    private double price;
    private double volume;
    private ChannelEnum channel;
    private EventDescriptionEnum eventDescription;
    private InstrumentTypeEnum instrumentType;
    private boolean bidSideAggressor;


    public MarketDataFeedEnum getMarketDataFeed() {
        return marketDataFeed;
    }

    public void setMarketDataFeed(MarketDataFeedEnum marketDataFeed) {
        this.marketDataFeed = marketDataFeed;
    }

    public TradingPairEnum getTradingPair() {
        return tradingPair;
    }

    public void setTradingPair(TradingPairEnum tradingPair) {
        this.tradingPair = tradingPair;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public ChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ChannelEnum channel) {
        this.channel = channel;
    }

    public EventDescriptionEnum getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(EventDescriptionEnum eventDescription) {
        this.eventDescription = eventDescription;
    }

    public InstrumentTypeEnum getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentTypeEnum instrumentType) {
        this.instrumentType = instrumentType;
    }

    public boolean isBidSideAggressor() {
        return bidSideAggressor;
    }

    public void setBidSideAggressor(boolean bidSideAggressor) {
        this.bidSideAggressor = bidSideAggressor;
    }
}
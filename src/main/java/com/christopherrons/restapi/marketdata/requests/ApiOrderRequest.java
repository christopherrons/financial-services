package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

public class ApiOrderRequest {

    private MarketDataFeedEnum marketDataFeed;
    private TradingPairEnum tradingPair;
    private double price;
    private double volume;
    private OrderTypeEnum orderType;
    private OrderOperationEnum orderOperation;
    private ChannelEnum channel;
    private EventDescriptionEnum eventDescription;
    private InstrumentTypeEnum instrumentType;


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

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public OrderOperationEnum getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(OrderOperationEnum orderOperation) {
        this.orderOperation = orderOperation;
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
}
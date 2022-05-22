package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

public class ApiSubscriptionRequest {
    private MarketDataFeedEnum dataFeedName;
    private TradingPairEnum tradingPair;
    private ChannelEnum channelName;

    public ChannelEnum getChannelName() {
        return channelName;
    }

    public void setChannelName(ChannelEnum channelName) {
        this.channelName = channelName;
    }

    public MarketDataFeedEnum getDataFeedName() {
        return dataFeedName;
    }

    public void setDataFeedName(MarketDataFeedEnum dataFeedName) {
        this.dataFeedName = dataFeedName;
    }

    public TradingPairEnum getTradingPair() {
        return tradingPair;
    }

    public void setTradingPair(TradingPairEnum tradingPair) {
        this.tradingPair = tradingPair;
    }

    @Override
    public String toString() {
        return "ApiSubscriptionRequest{" +
                "dataFeedName='" + dataFeedName + '\'' +
                ", tradingPair='" + tradingPair + '\'' +
                ", channelName='" + channelName + '\'' +
                '}';
    }
}

package com.christopherrons.restapi.marketdata.requests;

public class ApiSubscriptionRequest {
    private String dataFeedName;
    private String tradingPair;
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDataFeedName() {
        return dataFeedName;
    }

    public void setDataFeedName(String dataFeedName) {
        this.dataFeedName = dataFeedName;
    }

    public String getTradingPair() {
        return tradingPair;
    }

    public void setTradingPair(String tradingPair) {
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

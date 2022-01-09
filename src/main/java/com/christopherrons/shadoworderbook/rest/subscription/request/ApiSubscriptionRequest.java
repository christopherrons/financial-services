package com.christopherrons.shadoworderbook.rest.subscription.request;

public class ApiSubscriptionRequest {
    private String exchangeName;
    private String tradingPair;
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
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
                "exchangeName='" + exchangeName + '\'' +
                ", tradingPair='" + tradingPair + '\'' +
                ", channelName='" + channelName + '\'' +
                '}';
    }
}

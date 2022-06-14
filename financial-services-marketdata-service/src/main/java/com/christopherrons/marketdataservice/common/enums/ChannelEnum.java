package com.christopherrons.marketdataservice.common.enums;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.marketdataservice.bitstamp.model.BitstampOrder;
import com.christopherrons.marketdataservice.bitstamp.model.BitstampTrade;

import java.util.Arrays;
import java.util.List;

public enum ChannelEnum {
    INVALID_CHANNEL(MarketDataFeedEnum.INVALID_DATA_FEED, "Invalid Channel", null),
    LIVE_ORDERS(MarketDataFeedEnum.BITSTAMP, "live_orders", BitstampOrder.class),
    LIVE_TRADES(MarketDataFeedEnum.BITSTAMP, "live_trades", BitstampTrade.class);

    private final MarketDataFeedEnum marketDataFeedEnum;
    private final String channelName;
    private final Class<? extends MarketDataEvent> decodingClass;

    ChannelEnum(MarketDataFeedEnum marketDataFeedEnum, String channelName, Class<? extends MarketDataEvent> decodingClass) {
        this.marketDataFeedEnum = marketDataFeedEnum;
        this.channelName = channelName;
        this.decodingClass = decodingClass;
    }

    public static ChannelEnum fromChannelName(final String channelName, final MarketDataFeedEnum marketDataFeedEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(marketDataFeedEnum)) {
            if (channelName.toLowerCase().equals(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static ChannelEnum inferChannelEnum(final String channelName, final MarketDataFeedEnum marketDataFeedEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(marketDataFeedEnum)) {
            if (channelName.contains(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static List<String> getAvailableChannelNamesByDataFeed(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .map(ChannelEnum::getChannelName)
                .toList();
    }

    public static List<ChannelEnum> getAvailableExchangeChannelEnums(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .toList();
    }

    public MarketDataFeedEnum getExchangeEnum() {
        return marketDataFeedEnum;
    }

    public String getChannelName() {
        return channelName;
    }

    public Class<? extends MarketDataEvent> getDecodingClass() {
        return decodingClass;
    }
}

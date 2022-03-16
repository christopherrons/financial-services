package com.christopherrons.marketdata.common.enums.subscription;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.bitstamp.model.BitstampOrder;
import com.christopherrons.marketdata.bitstamp.model.BitstampTrade;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChannelEnum {
    INVALID_CHANNEL(MargetDataFeedEnum.INVALID_DATA_FEED, "Invalid Channel", null),
    LIVE_ORDERS(MargetDataFeedEnum.BITSTAMP, "live_orders", BitstampOrder.class),
    LIVE_TRADES(MargetDataFeedEnum.BITSTAMP, "live_trades", BitstampTrade.class);

    private final MargetDataFeedEnum margetDataFeedEnum;
    private final String channelName;
    private final Class<? extends MarketDataEvent> decodingClass;

    ChannelEnum(MargetDataFeedEnum margetDataFeedEnum, String channelName, Class<? extends MarketDataEvent> decodingClass) {
        this.margetDataFeedEnum = margetDataFeedEnum;
        this.channelName = channelName;
        this.decodingClass = decodingClass;
    }

    public static ChannelEnum fromChannelName(final String channelName, final MargetDataFeedEnum margetDataFeedEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(margetDataFeedEnum)) {
            if (channelName.toLowerCase().equals(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static ChannelEnum inferChannelEnum(final String channelName, final MargetDataFeedEnum margetDataFeedEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(margetDataFeedEnum)) {
            if (channelName.contains(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static List<String> getAvailableChannelNamesByDataFeed(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .map(ChannelEnum::getChannelName)
                .toList();
    }

    public static List<ChannelEnum> getAvailableExchangeChannelEnums(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .toList();
    }

    public MargetDataFeedEnum getExchangeEnum() {
        return margetDataFeedEnum;
    }

    public String getChannelName() {
        return channelName;
    }

    public Class<? extends MarketDataEvent> getDecodingClass() {
        return decodingClass;
    }
}

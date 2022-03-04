package com.christopherrons.marketdata.common.enums.subscription;

import com.christopherrons.marketdata.bitstamp.model.BitstampTrade;
import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.bitstamp.model.BitstampOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChannelEnum {
    INVALID_CHANNEL(MargetDataEnum.INVALID_EXCHANGE, "Invalid Channel", null),
    LIVE_ORDERS(MargetDataEnum.BITSTAMP, "live_orders", BitstampOrder.class),
    LIVE_TRADES(MargetDataEnum.BITSTAMP, "live_trades", BitstampTrade.class);

    private final MargetDataEnum margetDataEnum;
    private final String channelName;
    private final Class<? extends MarketDataEvent> decodingClass;

    ChannelEnum(MargetDataEnum margetDataEnum, String channelName, Class<? extends MarketDataEvent> decodingClass) {
        this.margetDataEnum = margetDataEnum;
        this.channelName = channelName;
        this.decodingClass = decodingClass;
    }

    public MargetDataEnum getExchangeEnum() {
        return margetDataEnum;
    }

    public String getChannelName() {
        return channelName;
    }

    public Class<? extends MarketDataEvent> getDecodingClass() {
        return decodingClass;
    }

    public static ChannelEnum fromChannelName(final String channelName, final MargetDataEnum margetDataEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(margetDataEnum)) {
            if (channelName.toLowerCase().equals(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static ChannelEnum inferChannelEnum(final String channelName, final MargetDataEnum margetDataEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(margetDataEnum)) {
            if (channelName.contains(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static List<String> getAvailableExchangeChannelNames(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(margetDataEnum))
                .map(ChannelEnum::getChannelName)
                .collect(Collectors.toList());
    }

    public static List<ChannelEnum> getAvailableExchangeChannelEnums(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(margetDataEnum))
                .collect(Collectors.toList());
    }
}

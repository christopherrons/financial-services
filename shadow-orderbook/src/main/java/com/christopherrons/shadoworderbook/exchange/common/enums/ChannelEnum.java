package com.christopherrons.shadoworderbook.exchange.common.enums;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.bitstamp.event.BitstampOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ChannelEnum {
    INVALID_CHANNEL(ExchangeEnum.INVALID_EXCHANGE, "Invalid Channel", null),
    LIVE_ORDERS(ExchangeEnum.BITSTAMP, "live_orders", BitstampOrder.class);

    private final ExchangeEnum exchangeEnum;
    private final String channelName;
    private final Class<? extends ExchangeEvent> decodingClass;

    ChannelEnum(ExchangeEnum exchangeEnum, String channelName, Class<? extends ExchangeEvent> decodingClass) {
        this.exchangeEnum = exchangeEnum;
        this.channelName = channelName;
        this.decodingClass = decodingClass;
    }

    public ExchangeEnum getExchangeEnum() {
        return exchangeEnum;
    }

    public String getChannelName() {
        return channelName;
    }

    public Class<? extends ExchangeEvent> getDecodingClass() {
        return decodingClass;
    }

    public static ChannelEnum fromChannelName(final String channelName, final ExchangeEnum exchangeEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(exchangeEnum)) {
            if (channelName.toLowerCase().equals(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static ChannelEnum inferChannelEnum(final String channelName, final ExchangeEnum exchangeEnum) {
        for (ChannelEnum channelEnum : getAvailableExchangeChannelEnums(exchangeEnum)) {
            if (channelName.contains(channelEnum.getChannelName())) {
                return channelEnum;
            }
        }
        return ChannelEnum.INVALID_CHANNEL;
    }

    public static List<String> getAvailableExchangeChannelNames(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(exchangeEnum))
                .map(ChannelEnum::getChannelName)
                .collect(Collectors.toList());
    }

    public static List<ChannelEnum> getAvailableExchangeChannelEnums(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(ChannelEnum.values())
                .filter(channelEnum -> !channelEnum.equals(ChannelEnum.INVALID_CHANNEL))
                .filter(channelEnum -> channelEnum.getExchangeEnum().equals(exchangeEnum))
                .collect(Collectors.toList());
    }
}

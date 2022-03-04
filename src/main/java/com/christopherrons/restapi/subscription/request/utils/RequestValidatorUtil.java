package com.christopherrons.restapi.subscription.request.utils;

import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.restapi.subscription.errorhandling.exceptions.ApiChannelRequestException;
import com.christopherrons.restapi.subscription.errorhandling.exceptions.ApiExchangeRequestException;
import com.christopherrons.restapi.subscription.errorhandling.exceptions.ApiTradingPairRequestException;

public class RequestValidatorUtil {

    public static MargetDataEnum extractAndValidateExchangeName(final String exchangeName) {
        MargetDataEnum extractedMargetDataEnum = MargetDataEnum.fromName(exchangeName);
        if (extractedMargetDataEnum.equals(MargetDataEnum.INVALID_EXCHANGE)) {
            throw (new ApiExchangeRequestException(exchangeName, MargetDataEnum.getExchangeNames()));
        }
        return extractedMargetDataEnum;
    }

    public static ChannelEnum extractAndValidateChannelName(final String channelName, final MargetDataEnum margetDataEnum) {
        ChannelEnum channelEnum = ChannelEnum.fromChannelName(channelName, margetDataEnum);
        if (channelEnum.equals(ChannelEnum.INVALID_CHANNEL)) {
            throw (new ApiChannelRequestException(channelName, ChannelEnum.getAvailableExchangeChannelNames(margetDataEnum)));
        }
        return channelEnum;
    }

    public static TradingPairEnum extractAndValidateTradingPair(final String tradingPair, final MargetDataEnum margetDataEnum) {
        TradingPairEnum tradingPairEnum = TradingPairEnum.fromTradingPair(tradingPair, margetDataEnum);
        if (tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR)) {
            throw (new ApiTradingPairRequestException(tradingPair, TradingPairEnum.getAvailableExchangeTradingPairs(margetDataEnum)));
        }
        return tradingPairEnum;
    }
}

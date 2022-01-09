package com.christopherrons.shadoworderbook.rest.subscription.request.utils;

import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;
import com.christopherrons.shadoworderbook.rest.subscription.errorhandling.exceptions.ApiChannelRequestException;
import com.christopherrons.shadoworderbook.rest.subscription.errorhandling.exceptions.ApiExchangeRequestException;
import com.christopherrons.shadoworderbook.rest.subscription.errorhandling.exceptions.ApiTradingPairRequestException;

public class RequestValidatorUtil {

    public static ExchangeEnum extractAndValidateExchangeName(final String exchangeName) {
        ExchangeEnum extractedExchangeEnum = ExchangeEnum.fromName(exchangeName);
        if (extractedExchangeEnum.equals(ExchangeEnum.INVALID_EXCHANGE)) {
            throw (new ApiExchangeRequestException(exchangeName, ExchangeEnum.getExchangeNames()));
        }
        return extractedExchangeEnum;
    }

    public static ChannelEnum extractAndValidateChannelName(final String channelName, final ExchangeEnum exchangeEnum) {
        ChannelEnum channelEnum = ChannelEnum.fromChannelName(channelName, exchangeEnum);
        if (channelEnum.equals(ChannelEnum.INVALID_CHANNEL)) {
            throw (new ApiChannelRequestException(channelName, ChannelEnum.getAvailableExchangeChannelNames(exchangeEnum)));
        }
        return channelEnum;
    }

    public static TradingPairEnum extractAndValidateTradingPair(final String tradingPair, final ExchangeEnum exchangeEnum) {
        TradingPairEnum tradingPairEnum = TradingPairEnum.fromTradingPair(tradingPair, exchangeEnum);
        if (tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR)) {
            throw (new ApiTradingPairRequestException(tradingPair, TradingPairEnum.getAvailableExchangeTradingPairs(exchangeEnum)));
        }
        return tradingPairEnum;
    }
}

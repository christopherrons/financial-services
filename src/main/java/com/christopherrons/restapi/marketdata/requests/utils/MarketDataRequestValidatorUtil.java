package com.christopherrons.restapi.marketdata.requests.utils;

import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.restapi.marketdata.exceptions.ApiChannelRequestException;
import com.christopherrons.restapi.marketdata.exceptions.ApiMarketDataFeedRequestException;
import com.christopherrons.restapi.marketdata.exceptions.ApiTradingPairRequestException;

public class MarketDataRequestValidatorUtil {

    private MarketDataRequestValidatorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static MargetDataFeedEnum extractAndValidateMarketDataFeedName(final String dataFeedName) {
        MargetDataFeedEnum extractedMargetDataFeedEnum = MargetDataFeedEnum.fromName(dataFeedName);
        if (extractedMargetDataFeedEnum.equals(MargetDataFeedEnum.INVALID_DATA_FEED)) {
            throw (new ApiMarketDataFeedRequestException(dataFeedName, MargetDataFeedEnum.getDataFeedNames()));
        }
        return extractedMargetDataFeedEnum;
    }

    public static ChannelEnum extractAndValidateChannelName(final String channelName, final MargetDataFeedEnum margetDataFeedEnum) {
        ChannelEnum channelEnum = ChannelEnum.fromChannelName(channelName, margetDataFeedEnum);
        if (channelEnum.equals(ChannelEnum.INVALID_CHANNEL)) {
            throw (new ApiChannelRequestException(channelName, ChannelEnum.getAvailableChannelNamesByDataFeed(margetDataFeedEnum)));
        }
        return channelEnum;
    }

    public static TradingPairEnum extractAndValidateTradingPair(final String tradingPair, final MargetDataFeedEnum margetDataFeedEnum) {
        TradingPairEnum tradingPairEnum = TradingPairEnum.fromTradingPair(tradingPair, margetDataFeedEnum);
        if (tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR)) {
            throw (new ApiTradingPairRequestException(tradingPair, TradingPairEnum.getAvailableTradingPairsByDataFeed(margetDataFeedEnum)));
        }
        return tradingPairEnum;
    }
}

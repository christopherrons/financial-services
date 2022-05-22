package com.christopherrons.restapi.marketdata.requests.utils;

import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.restapi.marketdata.exceptions.ApiChannelRequestException;
import com.christopherrons.restapi.marketdata.exceptions.ApiMarketDataFeedRequestException;
import com.christopherrons.restapi.marketdata.exceptions.ApiTradingPairRequestException;

public class MarketDataRequestValidatorUtil {

    private MarketDataRequestValidatorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static MarketDataFeedEnum extractAndValidateMarketDataFeedName(final String dataFeedName) {
        MarketDataFeedEnum extractedMarketDataFeedEnum = MarketDataFeedEnum.fromName(dataFeedName);
        if (extractedMarketDataFeedEnum.equals(MarketDataFeedEnum.INVALID_DATA_FEED)) {
            throw (new ApiMarketDataFeedRequestException(dataFeedName, MarketDataFeedEnum.getDataFeedNames()));
        }
        return extractedMarketDataFeedEnum;
    }

    public static ChannelEnum extractAndValidateChannelName(final String channelName, final MarketDataFeedEnum marketDataFeedEnum) {
        ChannelEnum channelEnum = ChannelEnum.fromChannelName(channelName, marketDataFeedEnum);
        if (channelEnum.equals(ChannelEnum.INVALID_CHANNEL)) {
            throw (new ApiChannelRequestException(channelName, ChannelEnum.getAvailableChannelNamesByDataFeed(marketDataFeedEnum)));
        }
        return channelEnum;
    }

    public static TradingPairEnum extractAndValidateTradingPair(final String tradingPair, final MarketDataFeedEnum marketDataFeedEnum) {
        TradingPairEnum tradingPairEnum = TradingPairEnum.fromTradingPair(tradingPair, marketDataFeedEnum);
        if (tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR)) {
            throw (new ApiTradingPairRequestException(tradingPair, TradingPairEnum.getAvailableTradingPairsByDataFeed(marketDataFeedEnum)));
        }
        return tradingPairEnum;
    }
}

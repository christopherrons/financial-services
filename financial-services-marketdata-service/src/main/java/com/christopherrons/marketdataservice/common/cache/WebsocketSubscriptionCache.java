package com.christopherrons.marketdataservice.common.cache;

import com.christopherrons.marketdataservice.api.MarketDataSubscription;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class WebsocketSubscriptionCache {

    private static final Logger LOGGER = Logger.getLogger(WebsocketSubscriptionCache.class.getName());

    private final Map<CompositeKey, MarketDataSubscription> keyToSubscription = new ConcurrentHashMap<>();

    public void addSubscription(final MarketDataFeedEnum marketDataFeedEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum, final MarketDataSubscription subscription) {
        CompositeKey key = new CompositeKey(marketDataFeedEnum, tradingPairEnum, channelEnum);
        if (keyToSubscription.containsKey(key)) {
            LOGGER.info(String.format("Subscription %s_%s already added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        } else {
            keyToSubscription.put(key, subscription);
            LOGGER.info(String.format("Subscription %s_%s added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        }
    }

    public Optional<MarketDataSubscription> getSubscription(final MarketDataFeedEnum marketDataFeedEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        CompositeKey key = new CompositeKey(marketDataFeedEnum, tradingPairEnum, channelEnum);
        LOGGER.info(String.format("Getting subscription %s_%s from cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        return Optional.ofNullable(keyToSubscription.get(key));
    }

    private record CompositeKey(MarketDataFeedEnum marketDataFeedEnum,
                                TradingPairEnum tradingPairEnum,
                                ChannelEnum channelEnum) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey that)) return false;
            return marketDataFeedEnum == that.marketDataFeedEnum && tradingPairEnum == that.tradingPairEnum && channelEnum == that.channelEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(marketDataFeedEnum, tradingPairEnum, channelEnum);
        }

    }
}

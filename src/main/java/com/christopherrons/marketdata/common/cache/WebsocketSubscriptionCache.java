package com.christopherrons.marketdata.common.cache;

import com.christopherrons.marketdata.api.MarketDataSubscription;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class WebsocketSubscriptionCache {

    private static final Logger LOGGER = Logger.getLogger(WebsocketSubscriptionCache.class.getName());

    private final Map<CompositeKey, MarketDataSubscription> keyToSubscription = new ConcurrentHashMap<>();

    public void addSubscription(final MargetDataFeedEnum margetDataFeedEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum, final MarketDataSubscription subscription) {
        CompositeKey key = new CompositeKey(margetDataFeedEnum, tradingPairEnum, channelEnum);
        if (keyToSubscription.containsKey(key)) {
            LOGGER.info(String.format("Subscription %s_%s already added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        } else {
            keyToSubscription.put(key, subscription);
            LOGGER.info(String.format("Subscription %s_%s added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        }
    }

    public Optional<MarketDataSubscription> getSubscription(final MargetDataFeedEnum margetDataFeedEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        CompositeKey key = new CompositeKey(margetDataFeedEnum, tradingPairEnum, channelEnum);
        LOGGER.info(String.format("Getting subscription %s_%s from cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        return Optional.ofNullable(keyToSubscription.get(key));
    }

    private record CompositeKey(MargetDataFeedEnum margetDataFeedEnum,
                                TradingPairEnum tradingPairEnum,
                                ChannelEnum channelEnum) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey that)) return false;
            return margetDataFeedEnum == that.margetDataFeedEnum && tradingPairEnum == that.tradingPairEnum && channelEnum == that.channelEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(margetDataFeedEnum, tradingPairEnum, channelEnum);
        }

    }
}

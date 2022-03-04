package com.christopherrons.marketdata.common.cache;

import com.christopherrons.marketdata.api.MarketDataSubscription;
import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class WebsocketSubscriptionCache {

    private static final Logger LOGGER = Logger.getLogger(WebsocketSubscriptionCache.class.getName());

    private final Map<CompositeKey, MarketDataSubscription> keyToSubscription = new ConcurrentHashMap<>();

    public void addSubscription(final MargetDataEnum margetDataEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum, final MarketDataSubscription subscription) {
        CompositeKey key = new CompositeKey(margetDataEnum, tradingPairEnum, channelEnum);
        if (keyToSubscription.containsKey(key)) {
            LOGGER.info(String.format("Subscription %s_%s already added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        } else {
            keyToSubscription.put(key, subscription);
            LOGGER.info(String.format("Subscription %s_%s added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        }
    }

    public Optional<MarketDataSubscription> getSubscription(final MargetDataEnum margetDataEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        CompositeKey key = new CompositeKey(margetDataEnum, tradingPairEnum, channelEnum);
        LOGGER.info(String.format("Getting subscription %s_%s from cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        return Optional.ofNullable(keyToSubscription.get(key));
    }

    private static class CompositeKey {

        private final MargetDataEnum margetDataEnum;
        private final TradingPairEnum tradingPairEnum;
        private final ChannelEnum channelEnum;

        public CompositeKey(MargetDataEnum margetDataEnum, TradingPairEnum tradingPairEnum, ChannelEnum channelEnum) {
            this.margetDataEnum = margetDataEnum;
            this.tradingPairEnum = tradingPairEnum;
            this.channelEnum = channelEnum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return margetDataEnum == that.margetDataEnum && tradingPairEnum == that.tradingPairEnum && channelEnum == that.channelEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(margetDataEnum, tradingPairEnum, channelEnum);
        }

    }
}

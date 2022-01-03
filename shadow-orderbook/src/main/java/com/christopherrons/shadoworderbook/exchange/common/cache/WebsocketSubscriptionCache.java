package com.christopherrons.shadoworderbook.exchange.common.cache;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeSubscription;
import com.christopherrons.shadoworderbook.exchange.common.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.TradingPairEnum;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class WebsocketSubscriptionCache {

    private static final Logger LOGGER = Logger.getLogger(WebsocketSubscriptionCache.class.getName());

    private final Map<CompositeKey, ExchangeSubscription> keyToSubscription = new ConcurrentHashMap<>();

    public void addSubscription(final ExchangeEnum exchangeEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum, final ExchangeSubscription subscription) {
        keyToSubscription.putIfAbsent(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum), subscription);
        LOGGER.info(String.format("Subscription %s_%s added to cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
    }

    public ExchangeSubscription getSubscription(final ExchangeEnum exchangeEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        LOGGER.info(String.format("Getting subscription %s_%s from cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        if (keyToSubscription.containsKey(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum))) {
            return keyToSubscription.get(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum));
        }
        return null;
    }

    public ExchangeSubscription removeSubscription(final ExchangeEnum exchangeEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        LOGGER.info(String.format("Subscription %s_%s removed from cache.", channelEnum.getChannelName(), tradingPairEnum.getName()));
        return keyToSubscription.remove(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum));
    }

    public boolean isSubscribed(final ExchangeEnum exchangeEnum, final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        if (keyToSubscription.containsKey(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum))) {
            return keyToSubscription.get(new CompositeKey(exchangeEnum, tradingPairEnum, channelEnum)).isSubscribed();
        }
        return false;
    }

    private static class CompositeKey {

        private final ExchangeEnum exchangeEnum;
        private final TradingPairEnum tradingPairEnum;
        private final ChannelEnum channelEnum;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return exchangeEnum == that.exchangeEnum && tradingPairEnum == that.tradingPairEnum && channelEnum == that.channelEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(exchangeEnum, tradingPairEnum, channelEnum);
        }

        public CompositeKey(ExchangeEnum exchangeEnum, TradingPairEnum tradingPairEnum, ChannelEnum channelEnum) {
            this.exchangeEnum = exchangeEnum;
            this.tradingPairEnum = tradingPairEnum;
            this.channelEnum = channelEnum;
        }

    }
}

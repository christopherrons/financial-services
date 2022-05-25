package com.christopherrons.marketdata.bitstamp.client;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataSubscription;
import com.christopherrons.marketdata.api.MarketDataWebsocketClient;
import com.christopherrons.marketdata.common.cache.WebsocketSubscriptionCache;
import com.christopherrons.marketdata.common.client.JsonMessageDecoder;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class BitstampWebsocketClient implements MarketDataWebsocketClient {

    private static final Logger LOGGER = Logger.getLogger(BitstampWebsocketClient.class.getName());
    private final WebsocketSubscriptionCache websocketSubscriptionCache = new WebsocketSubscriptionCache();


    public void subscribe(final TradingPairEnum tradingPairEnum,
                          final ChannelEnum channelEnum,
                          Consumer<MarketDataEvent> eventHandler) throws DeploymentException, IOException {
        BitstampSubscription subscription = new BitstampSubscription(
                new JsonMessageDecoder(channelEnum.getDecodingClass()),
                eventHandler,
                channelEnum,
                tradingPairEnum
        );
        subscription.subscribe();
        websocketSubscriptionCache.addSubscription(MarketDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum, subscription);
    }

    public void unsubscribe(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        Optional<MarketDataSubscription> subscription = websocketSubscriptionCache.getSubscription(MarketDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum);
        subscription.ifPresent(MarketDataSubscription::unsubscribe);
    }

    public boolean isSubscribed(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        Optional<MarketDataSubscription> subscription = websocketSubscriptionCache.getSubscription(MarketDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum);
        return subscription.map(MarketDataSubscription::isSubscribed).orElse(false);
    }
}

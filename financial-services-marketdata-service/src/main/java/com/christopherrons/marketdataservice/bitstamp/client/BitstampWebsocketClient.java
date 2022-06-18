package com.christopherrons.marketdataservice.bitstamp.client;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.marketdataservice.api.MarketDataSubscription;
import com.christopherrons.marketdataservice.api.MarketDataWebsocketClient;
import com.christopherrons.marketdataservice.common.cache.WebsocketSubscriptionCache;
import com.christopherrons.marketdataservice.common.client.JsonMessageDecoder;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

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

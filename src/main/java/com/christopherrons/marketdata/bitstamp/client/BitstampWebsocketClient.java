package com.christopherrons.marketdata.bitstamp.client;

import com.christopherrons.marketdata.api.MarketDataSubscription;
import com.christopherrons.marketdata.api.MarketDataWebsocketClient;
import com.christopherrons.marketdata.common.cache.WebsocketSubscriptionCache;
import com.christopherrons.marketdata.common.client.JsonMessageDecoder;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class BitstampWebsocketClient implements MarketDataWebsocketClient {

    private static final Logger LOGGER = Logger.getLogger(BitstampWebsocketClient.class.getName());

    @Autowired
    private MarketDataService marketDataService;

    @Autowired
    private WebsocketSubscriptionCache websocketSubscriptionCache;

    public void subscribe(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) throws DeploymentException, IOException {
        BitstampSubscription subscription = new BitstampSubscription(
                new JsonMessageDecoder(channelEnum.getDecodingClass()),
                marketDataService,
                channelEnum,
                tradingPairEnum
        );
        subscription.subscribe();
        websocketSubscriptionCache.addSubscription(MargetDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum, subscription);
    }

    public void unsubscribe(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        Optional<MarketDataSubscription> subscription = websocketSubscriptionCache.getSubscription(MargetDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum);
        subscription.ifPresent(MarketDataSubscription::unsubscribe);
    }

    public boolean isSubscribed(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        Optional<MarketDataSubscription> subscription = websocketSubscriptionCache.getSubscription(MargetDataFeedEnum.BITSTAMP, tradingPairEnum, channelEnum);
        return subscription.map(MarketDataSubscription::isSubscribed).orElse(false);
    }
}

package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeSubscription;
import com.christopherrons.shadoworderbook.exchange.api.ExchangeWebsocketClient;
import com.christopherrons.shadoworderbook.exchange.common.cache.WebsocketSubscriptionCache;
import com.christopherrons.shadoworderbook.exchange.common.client.JsonMessageDecoder;
import com.christopherrons.shadoworderbook.exchange.common.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.TradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.service.EventHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class BitstampWebsocketClient implements ExchangeWebsocketClient {

    private static final Logger LOGGER = Logger.getLogger(BitstampWebsocketClient.class.getName());

    @Autowired
    private EventHandlerService eventHandlerService;

    @Autowired
    private WebsocketSubscriptionCache websocketSubscriptionCache;

    public void subscribe(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) throws DeploymentException, IOException {
        BitstampSubscription subscription = new BitstampSubscription(new JsonMessageDecoder(channelEnum.getDecodingClass()), eventHandlerService, channelEnum, tradingPairEnum);
        websocketSubscriptionCache.addSubscription(ExchangeEnum.BITSTAMP, tradingPairEnum, channelEnum, subscription);
        subscription.subscribe();
    }

    public void unsubscribe(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        ExchangeSubscription subscription = websocketSubscriptionCache.removeSubscription(ExchangeEnum.BITSTAMP, tradingPairEnum, channelEnum);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public boolean isSubscribed(final TradingPairEnum tradingPairEnum, final ChannelEnum channelEnum) {
        return websocketSubscriptionCache.isSubscribed(ExchangeEnum.BITSTAMP, tradingPairEnum, channelEnum);
    }
}

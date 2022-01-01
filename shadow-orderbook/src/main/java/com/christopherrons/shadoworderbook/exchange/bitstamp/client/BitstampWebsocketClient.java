package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeWebsocketClient;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampChannelEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampTradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.event.BitstampOrder;
import com.christopherrons.shadoworderbook.exchange.common.client.EventHandlerService;
import com.christopherrons.shadoworderbook.exchange.common.client.JsonMessageDecoder;
import com.christopherrons.shadoworderbook.exchange.common.client.MessageHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class BitstampWebsocketClient implements ExchangeWebsocketClient {

    private static final Logger LOGGER = Logger.getLogger(BitstampWebsocketClient.class.getName());
    private final Map<CompositeKey, BitstampSubscription> keyToSubscription = new ConcurrentHashMap<>();

    @Autowired
    private EventHandlerService eventHandlerService;

    public void subscribeToLiveOrders() throws DeploymentException, IOException {
        MessageHandler messageHandler = MessageHandlerFactory.createMessageHandler(new JsonMessageDecoder<>(BitstampOrder.class), eventHandlerService);
        subscribe(BitstampTradingPairEnum.XRP_USD, BitstampChannelEnum.LIVE_ORDERS, messageHandler);
    }

    private void subscribe(final BitstampTradingPairEnum tradingPairEnum, final BitstampChannelEnum channelEnum, final MessageHandler messageHandler) throws DeploymentException, IOException {
        BitstampSubscription connection = new BitstampSubscription(messageHandler, channelEnum, tradingPairEnum);
        connection.subscribe();
        keyToSubscription.putIfAbsent(new CompositeKey(tradingPairEnum, channelEnum), connection);
    }

    private void unsubscribe(final BitstampTradingPairEnum tradingPairEnum, final BitstampChannelEnum channelEnum) {
        BitstampSubscription subscription = keyToSubscription.remove(new CompositeKey(tradingPairEnum, channelEnum));
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private static class CompositeKey {

        private final BitstampTradingPairEnum bitstampTradingPairEnum;
        private final BitstampChannelEnum bitstampChannelEnum;

        CompositeKey(BitstampTradingPairEnum bitstampTradingPairEnum, BitstampChannelEnum bitstampChannelEnum) {
            this.bitstampTradingPairEnum = bitstampTradingPairEnum;
            this.bitstampChannelEnum = bitstampChannelEnum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return bitstampTradingPairEnum == that.bitstampTradingPairEnum && bitstampChannelEnum == that.bitstampChannelEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(bitstampTradingPairEnum, bitstampChannelEnum);
        }
    }

}

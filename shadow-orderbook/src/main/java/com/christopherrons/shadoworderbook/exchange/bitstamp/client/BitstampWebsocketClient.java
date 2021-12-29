package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeWebsocketClient;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampChannelEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampTradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.event.BitstampOrder;
import com.christopherrons.shadoworderbook.exchange.common.client.MessageDecoder;
import com.christopherrons.shadoworderbook.exchange.common.client.MessageHandlerFactoryService;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class BitstampWebsocketClient implements ExchangeWebsocketClient {

    private static final Logger LOGGER = Logger.getLogger(BitstampWebsocketClient.class.getName());
    private static final ExchangeEnum EXCHANGE_ENUM = ExchangeEnum.BITSTAMP;

    private final Map<CompositeKey, BitstampConnection> subscriptionToConnection = new ConcurrentHashMap<>();
    private Session session;

    @Autowired
    private MessageHandlerFactoryService<BitstampOrder> messageHandlerFactoryService;

    public void subscribeToLiveOrders() throws DeploymentException, IOException, URISyntaxException {
        MessageHandler messageHandler = messageHandlerFactoryService.createMessageHandler(new MessageDecoder<>(BitstampOrder.class));
        for (BitstampTradingPairEnum tradingPairEnum : BitstampTradingPairEnum.values()) {
            subscribe(BitstampTradingPairEnum.XRP_USD, BitstampChannelEnum.LIVE_ORDERS, messageHandler);
        }
    }

    private void subscribe(final BitstampTradingPairEnum tradingPairEnum, final BitstampChannelEnum channelEnum, final MessageHandler messageHandler) throws DeploymentException, IOException, URISyntaxException {
        BitstampConnection connection = new BitstampConnection(tradingPairEnum, channelEnum);
        connection.connectToChannel(messageHandler, EXCHANGE_ENUM);
        subscriptionToConnection.putIfAbsent(new CompositeKey(tradingPairEnum, channelEnum), connection);
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

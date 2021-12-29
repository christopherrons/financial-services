package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeWebsocketChannel;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampChannelEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampTradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.client.CustomClientEndpoint;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

import javax.json.Json;
import javax.websocket.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class BitstampConnection implements ExchangeWebsocketChannel {
    private static final Logger LOGGER = Logger.getLogger(BitstampConnection.class.getName());

    private final String subscription;
    private final String channel;
    private Session session;

    public BitstampConnection(BitstampTradingPairEnum bitstampTradingPairEnum, BitstampChannelEnum bitstampChannelEnum) {
        this.channel = createChannel(bitstampChannelEnum, bitstampTradingPairEnum);
        this.subscription = createSubscriptionJson();
    }

    private String createChannel(final BitstampChannelEnum bitstampChannelEnum, final BitstampTradingPairEnum bitstampTradingPairEnum) {
        return String.format("%s_%s", bitstampChannelEnum.getValue(), bitstampTradingPairEnum.getValue());
    }

    private String createSubscriptionJson() {
        return Json.createObjectBuilder()
                .add("event", "bts:subscribe")
                .add("data", Json.createObjectBuilder().add("channel", channel))
                .build()
                .toString();
    }

    public void connectToChannel(final MessageHandler messageHandler, final ExchangeEnum exchangeEnum) throws DeploymentException, IOException, URISyntaxException {
        LOGGER.info(String.format("Attempting to connect to: %s", exchangeEnum.getUriString()));

        final WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        this.session = webSocketContainer.connectToServer(new CustomClientEndpoint(subscription, exchangeEnum, messageHandler),
                exchangeEnum.getUri());
    }
}

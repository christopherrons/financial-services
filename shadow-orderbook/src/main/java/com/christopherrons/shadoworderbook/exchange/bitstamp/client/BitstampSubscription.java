package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeSubscription;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampChannelEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampTradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.client.CustomClientEndpoint;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import com.christopherrons.shadoworderbook.helper.ThreadWrapper;

import javax.json.Json;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class BitstampSubscription implements ExchangeSubscription {
    private static final Logger LOGGER = Logger.getLogger(BitstampSubscription.class.getName());
    private static final URI websocketURI = ExchangeEnum.BITSTAMP.getUri();

    private final BitstampChannelEnum bitstampChannelEnum;
    private final BitstampTradingPairEnum bitstampTradingPairEnum;
    private Session session;

    public BitstampSubscription(MessageHandler messageHandler, BitstampChannelEnum bitstampChannelEnum, BitstampTradingPairEnum bitstampTradingPairEnum) throws DeploymentException, IOException {
        this.bitstampChannelEnum = bitstampChannelEnum;
        this.bitstampTradingPairEnum = bitstampTradingPairEnum;
        this.session = createSession(messageHandler);
    }

    private Session createSession(final MessageHandler messageHandler) throws DeploymentException, IOException {
        LOGGER.info(String.format("Attempting to connect to: %s.", websocketURI));

        final WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        Session session = webSocketContainer.connectToServer(new CustomClientEndpoint(messageHandler),
                websocketURI);

        if (session.isOpen()) {
            LOGGER.info(String.format("Successfully connected to: %s.", websocketURI));
        }

        return session;
    }

    public void subscribe() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadWrapper(String.format("Sub: %s", bitstampTradingPairEnum.getValue())));
        executorService.execute(() -> {
            int timeout = 30;
            int timeWaited = 0;
            while (!session.isOpen()) {
                try {
                    Thread.sleep(timeout * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeWaited = timeWaited + timeout;
                LOGGER.info(String.format("Waiting for session to open before subscribing to: %s. Total time waited %s.", createChannel(), timeWaited));
            }

            LOGGER.info(String.format("Attempting to subscribe to: %s.", createChannel()));
            RemoteEndpoint.Basic basicRemoteEndpoint = session.getBasicRemote();
            try {
                basicRemoteEndpoint.sendObject(createSubscriptionJson());
                LOGGER.info(String.format("Successfully subscribed to: %s.", createChannel()));
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public void unsubscribe() {
        LOGGER.info(String.format("Attempting to unsubscribe to: %s", createChannel()));
        RemoteEndpoint.Basic basicRemoteEndpoint = session.getBasicRemote();
        try {
            basicRemoteEndpoint.sendObject(createUnsubscribeJson());
            session.close();
            LOGGER.info(String.format("Successfully unsubscribe to: %s and closed session.", createChannel()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private String createSubscriptionJson() {
        return createSubscriptionRelatedJson("bts:subscribe");
    }

    private String createUnsubscribeJson() {
        return createSubscriptionRelatedJson("bts:unsubscribe");
    }

    private String createSubscriptionRelatedJson(final String subscriptionType) {
        return Json.createObjectBuilder()
                .add("event", subscriptionType)
                .add("data", Json.createObjectBuilder().add("channel", createChannel()))
                .build()
                .toString();
    }

    private String createChannel() {
        return String.format("%s_%s", bitstampChannelEnum.getValue(), bitstampTradingPairEnum.getValue());
    }
}

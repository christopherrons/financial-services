package com.christopherrons.shadoworderbook.exchange.bitstamp.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.api.ExchangeSubscription;
import com.christopherrons.shadoworderbook.exchange.common.client.CustomClientEndpoint;
import com.christopherrons.shadoworderbook.exchange.common.client.JsonMessageDecoder;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.service.EventHandlerService;
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

    private final ChannelEnum channelEnum;
    private final TradingPairEnum tradingPairEnum;
    private Session session;
    private boolean isSubscribed = false;

    public BitstampSubscription(JsonMessageDecoder messageDecoder, EventHandlerService eventHandlerService,
                                ChannelEnum channelEnum, TradingPairEnum tradingPairEnum) throws DeploymentException, IOException {
        this.channelEnum = channelEnum;
        this.tradingPairEnum = tradingPairEnum;
        this.session = createSession(createMessageHandler(messageDecoder, eventHandlerService));
    }

    private MessageHandler createMessageHandler(final JsonMessageDecoder messageDecoder, final EventHandlerService eventHandlerService) {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ExchangeEvent event = messageDecoder.decodeMessage(message);
                switch (event.getEventDescriptionEnum()) {
                    case SUBSCRIPTION_SUCCEEDED:
                        isSubscribed = true;
                        LOGGER.info(String.format("Successfully subscribed to: %s.", createChannel()));
                        break;
                    case FORCED_RECONNECT:
                        LOGGER.warning("Forced reconnect received!");
                        isSubscribed = false; //TODO: Handle such that it reconnects
                        break;
                    default:
                        eventHandlerService.handleEvent(event);
                }
            }
        };
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
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadWrapper(String.format("Sub: %s", tradingPairEnum.getName())));
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
            isSubscribed = false;
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
        return String.format("%s_%s", channelEnum.getChannelName(), tradingPairEnum.getName());
    }

    @Override
    public boolean isSubscribed() {
        return isSubscribed;
    }
}

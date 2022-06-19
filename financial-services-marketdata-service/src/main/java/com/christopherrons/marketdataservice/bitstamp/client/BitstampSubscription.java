package com.christopherrons.marketdataservice.bitstamp.client;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.misc.wrappers.ThreadWrapper;
import com.christopherrons.marketdataservice.api.MarketDataSubscription;
import com.christopherrons.marketdataservice.bitstamp.model.BitstampEvent;
import com.christopherrons.marketdataservice.common.client.CustomClientEndpoint;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BitstampSubscription implements MarketDataSubscription {
    private static final Logger LOGGER = LoggerFactory.getLogger(BitstampSubscription.class);
    private static final URI websocketURI = MarketDataFeedEnum.BITSTAMP.getUri();
    private static final String SUBSCRIBE = "bts:subscribe";
    private static final String UNSUBSCRIBE = "bts:unsubscribe";
    private static final String HEART_BEAT = "bts:heartbeat";
    private static final BitstampJsonMessageDecoder bitstampEventDecoder = new BitstampJsonMessageDecoder(BitstampEvent.class);

    private final ChannelEnum channelEnum;
    private final TradingPairEnum tradingPairEnum;
    private Session session;
    private boolean isSubscribed = false;

    private final ScheduledExecutorService heartBeatExecutorService = Executors.newScheduledThreadPool(1);

    public BitstampSubscription(Consumer<MarketDataEvent> eventHandler,
                                ChannelEnum channelEnum,
                                TradingPairEnum tradingPairEnum) throws DeploymentException, IOException {
        this.channelEnum = channelEnum;
        this.tradingPairEnum = tradingPairEnum;
        this.session = createSession(createMessageHandler(eventHandler));
        startHeartBeats();
    }

    private MessageHandler createMessageHandler(final Consumer<MarketDataEvent> eventHandler) {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                BitstampEvent event = bitstampEventDecoder.decodeMessage(message);
                if (event.getEventDescriptionEnum() != null) {
                    handleEvent(event, eventHandler);
                } else {
                    LOGGER.info(String.format("Message: %s not decodeable.", message));
                }
            }
        };
    }

    private void handleEvent(final BitstampEvent event, final Consumer<MarketDataEvent> eventHandler) {
        switch (event.getEventDescriptionEnum()) {
            case SUBSCRIPTION_SUCCEEDED -> {
                isSubscribed = true;
                LOGGER.info(String.format("Successfully subscribed to: %s.", createChannel()));
            }
            case HEART_BEAT -> {
                if (event.getHeartBeat().isSuccessful()) {
                    LOGGER.info(String.format("Heartbeat successful %s." +
                            " Session status: %s, isSubscribed status: %s.", tradingPairEnum.getName(), session.isOpen(), isSubscribed));
                } else {
                    LOGGER.warn(String.format("Heartbeat NOT successful %s. Event: %" +
                            " Session status: %s, isSubscribed status: %s.", tradingPairEnum.getName(), event, session.isOpen(), isSubscribed));
                }
            }
            case FORCED_RECONNECT -> {
                LOGGER.warn("Forced reconnect received!");
                isSubscribed = false;
                subscribe();
            }
            case ORDER_CREATED, ORDER_DELETED, ORDER_UPDATED -> eventHandler.accept(event.getOrder());
            case TRADE -> eventHandler.accept(event.getTrade());
            default -> LOGGER.warn(String.format("Unhandled Bitstamp event received %s: ", event));
        }
    }

    private Session createSession(final MessageHandler messageHandler) throws DeploymentException, IOException {
        LOGGER.info(String.format("Attempting to connect to: %s.", websocketURI));

        final WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        session = webSocketContainer.connectToServer(new CustomClientEndpoint(messageHandler),
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
                    Thread.sleep(timeout * 1000L);
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
            heartBeatExecutorService.shutdown();
            session.close();
            LOGGER.info(String.format("Successfully unsubscribe to: %s and closed session.", createChannel()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private void startHeartBeats() {
        RemoteEndpoint.Basic basicRemoteEndpoint = session.getBasicRemote();
        heartBeatExecutorService.scheduleAtFixedRate(() -> {
                    try {
                        basicRemoteEndpoint.sendObject(createHeartBeatJson());
                    } catch (Exception e) {
                        LOGGER.warn("Could not run heartbeat");
                    }
                },
                0, 30, TimeUnit.SECONDS);
    }

    private String createSubscriptionJson() {
        return createSubscriptionRelatedJson(SUBSCRIBE);
    }

    private String createUnsubscribeJson() {
        return createSubscriptionRelatedJson(UNSUBSCRIBE);
    }

    private String createSubscriptionRelatedJson(final String subscriptionType) {
        return Json.createObjectBuilder()
                .add("event", subscriptionType)
                .add("data", Json.createObjectBuilder().add("channel", createChannel()))
                .build()
                .toString();
    }

    private String createHeartBeatJson() {
        return Json.createObjectBuilder()
                .add("event", HEART_BEAT)
                .build()
                .toString();
    }

    private String createChannel() {
        return String.format("%s_%s", channelEnum.getChannelName(), tradingPairEnum.getName());
    }

    @Override
    public boolean isSubscribed() {
        return isSubscribed && session.isOpen();
    }
}

package com.christopherrons.marketdata.bitstamp.client;

import com.christopherrons.common.misc.wrappers.ThreadWrapper;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataSubscription;
import com.christopherrons.marketdata.common.client.CustomClientEndpoint;
import com.christopherrons.marketdata.common.client.JsonMessageDecoder;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.MarketDataService;

import javax.json.Json;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class BitstampSubscription implements MarketDataSubscription {
    private static final Logger LOGGER = Logger.getLogger(BitstampSubscription.class.getName());
    private static final URI websocketURI = MargetDataFeedEnum.BITSTAMP.getUri();
    private static final String SUBSCRIBE = "bts:subscribe";
    private static final String UNSUBSCRIBE = "bts:unsubscribe";


    private final ChannelEnum channelEnum;
    private final TradingPairEnum tradingPairEnum;
    private Session session;
    private boolean isSubscribed = false;

    public BitstampSubscription(JsonMessageDecoder messageDecoder, MarketDataService marketDataService,
                                ChannelEnum channelEnum, TradingPairEnum tradingPairEnum) throws DeploymentException, IOException {
        this.channelEnum = channelEnum;
        this.tradingPairEnum = tradingPairEnum;
        this.session = createSession(createMessageHandler(messageDecoder, marketDataService));
    }

    private MessageHandler createMessageHandler(final JsonMessageDecoder messageDecoder, final MarketDataService marketDataService) {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                MarketDataEvent event = messageDecoder.decodeMessage(message);
                switch (event.getEventDescriptionEnum()) {
                    case SUBSCRIPTION_SUCCEEDED:
                        isSubscribed = true;
                        LOGGER.info(String.format("Successfully subscribed to: %s.", createChannel()));
                        break;
                    case FORCED_RECONNECT:
                        LOGGER.warning("Forced reconnect received!");
                        isSubscribed = false;
                        subscribe();
                        break;
                    default:
                        marketDataService.handleEvent(event);
                }
            }
        };
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
            session.close();
            LOGGER.info(String.format("Successfully unsubscribe to: %s and closed session.", createChannel()));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
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

    private String createChannel() {
        return String.format("%s_%s", channelEnum.getChannelName(), tradingPairEnum.getName());
    }

    @Override
    public boolean isSubscribed() {
        return isSubscribed;
    }
}

package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

import javax.websocket.*;
import java.io.IOException;
import java.util.logging.Logger;


@ClientEndpoint
public final class CustomClientEndpoint extends Endpoint {
    private static final Logger LOGGER = Logger.getLogger(CustomClientEndpoint.class.getName());
    private final String subscription;
    private final MessageHandler messageHandler;
    private final ExchangeEnum exchangeEnum;


    public CustomClientEndpoint(String subscription, ExchangeEnum exchangeEnum, MessageHandler messageHandler) {
        this.subscription = subscription;
        this.exchangeEnum = exchangeEnum;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info(String.format("Connected to: %s", exchangeEnum.getUriString()));
        if (!session.getMessageHandlers().contains(messageHandler)) {
            session.addMessageHandler(messageHandler);
        }

        initSubscription(session);
    }

    private void initSubscription(final Session session) {
        RemoteEndpoint.Basic basicRemoteEndpoint = session.getBasicRemote();
        try {
            basicRemoteEndpoint.sendObject(subscription);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}


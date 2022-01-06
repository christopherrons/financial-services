package com.christopherrons.shadoworderbook.exchange.common.client;

import javax.websocket.*;
import java.util.logging.Logger;


@ClientEndpoint
public final class CustomClientEndpoint extends Endpoint {
    private static final Logger LOGGER = Logger.getLogger(CustomClientEndpoint.class.getName());
    private final MessageHandler messageHandler;

    public CustomClientEndpoint(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("Session opened!");
        if (!session.getMessageHandlers().contains(messageHandler)) {
            session.addMessageHandler(messageHandler);
        }
    }
}


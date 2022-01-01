package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;

import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ExchangeWebsocketChannel {

    void connectToChannel(final MessageHandler messageHandler, final ExchangeEnum exchangeEnum) throws DeploymentException, IOException, URISyntaxException;
}

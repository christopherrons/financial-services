package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;

import javax.websocket.MessageHandler;

public class MessageHandlerFactory {

    public static MessageHandler createMessageHandler(final JsonMessageDecoder<? extends ExchangeEvent> messageDecoder, final EventHandlerService eventHandlerService) {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ExchangeEvent event = messageDecoder.decodeMessage(message);
                eventHandlerService.handleEvent(event);
            }
        };
    }
}

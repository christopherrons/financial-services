package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.common.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.MessageHandler;
import java.util.logging.Logger;

@Service
public class MessageHandlerFactoryService<T extends Event> {

    private static final Logger LOGGER = Logger.getLogger(MessageHandlerFactoryService.class.getName());

    @Autowired
    private EventHandlerService eventHandlerService;

    public MessageHandler createMessageHandler(final MessageDecoder<T> messageDecoder) {
        return createOrderMessageHandler(messageDecoder);
    }

    private MessageHandler createOrderMessageHandler(final MessageDecoder<T> messageDecoder) {
        return new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ExchangeEvent event = messageDecoder.decodeToExchangeEvent(message);
                eventHandlerService.handleEvent(event);
            }
        };
    }
}

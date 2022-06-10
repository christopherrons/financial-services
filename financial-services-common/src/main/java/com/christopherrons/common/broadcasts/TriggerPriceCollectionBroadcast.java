package com.christopherrons.common.broadcasts;

import org.springframework.context.ApplicationEvent;

public class TriggerPriceCollectionBroadcast extends ApplicationEvent {

    public TriggerPriceCollectionBroadcast(Object source) {
        super(source);
    }

}
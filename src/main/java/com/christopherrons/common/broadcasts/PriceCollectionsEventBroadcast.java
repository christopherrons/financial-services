package com.christopherrons.common.broadcasts;

import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import org.springframework.context.ApplicationEvent;

public class PriceCollectionsEventBroadcast extends ApplicationEvent {

    private final PriceCollection priceCollection;

    public PriceCollectionsEventBroadcast(Object source, PriceCollection priceCollection) {
        super(source);
        this.priceCollection = priceCollection;
    }


    public PriceCollection getPriceCollection() {
        return priceCollection;
    }
}
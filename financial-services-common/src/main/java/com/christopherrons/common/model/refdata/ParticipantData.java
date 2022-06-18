package com.christopherrons.common.model.refdata;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParticipantData {

    private final Participant participant;
    private final Set<String> openOrders = ConcurrentHashMap.newKeySet();


    public ParticipantData(Participant participant) {
        this.participant = participant;
    }

    public void addOrderId(final String orderId) {
        openOrders.add(orderId);
    }

    public void removeOrderId(final String orderId) {
        openOrders.remove(orderId);
    }
}

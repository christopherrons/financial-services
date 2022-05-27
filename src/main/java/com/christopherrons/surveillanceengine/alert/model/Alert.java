package com.christopherrons.surveillanceengine.alert.model;

import java.util.List;

public record Alert(String alertRuleName, String orderbook, boolean isTriggered, List<Breach> breaches) {

    @Override
    public String toString() {
        return "Alert{" +
                "alertRuleName='" + alertRuleName + '\'' +
                ", orderbook='" + orderbook + '\'' +
                ", isTriggered=" + isTriggered +
                ", breaches=" + breaches +
                '}';
    }
}

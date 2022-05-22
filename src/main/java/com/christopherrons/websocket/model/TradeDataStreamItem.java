package com.christopherrons.websocket.model;

public record TradeDataStreamItem(double price, double volume, boolean isBidSideAggressor) {

}

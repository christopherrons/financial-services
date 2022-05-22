package com.christopherrons.websocket.datastream.model;

public record TradeDataStreamItem(double price, double volume, boolean isBidSideAggressor) {

}

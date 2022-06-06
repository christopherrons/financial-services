package com.christopherrons.websockets.datastream.model;

public record TradeDataStreamItem(double price, double volume, boolean isBidSideAggressor) {

}

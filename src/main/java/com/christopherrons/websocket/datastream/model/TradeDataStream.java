package com.christopherrons.websocket.datastream.model;

import com.christopherrons.websocket.api.DataStream;

import java.util.List;

public record TradeDataStream(List<TradeDataStreamItem> tradeDataStreamItems) implements DataStream {

    public boolean isEmpty() {
        return tradeDataStreamItems.isEmpty();
    }
}

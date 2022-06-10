package com.christopherrons.websockets.datastream.model;

import com.christopherrons.websockets.api.DataStream;

import java.util.List;

public record TradeDataStream(List<TradeDataStreamItem> tradeDataStreamItems) implements DataStream {

    public boolean isEmpty() {
        return tradeDataStreamItems.isEmpty();
    }
}

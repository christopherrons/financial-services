package com.christopherrons.websocket.datastream.model;

import com.christopherrons.websocket.api.DataStream;

import java.util.List;

public record OrderDataStream(List<OrderDataStreamItem> orderDataStreamItems) implements DataStream {

    public boolean isEmpty() {
        return orderDataStreamItems.isEmpty();
    }

}

package com.christopherrons.websockets.datastream.model;

import com.christopherrons.websockets.api.DataStream;

import java.util.List;

public record OrderDataStream(List<OrderDataStreamItem> orderDataStreamItems) implements DataStream {

    public boolean isEmpty() {
        return orderDataStreamItems.isEmpty();
    }

}

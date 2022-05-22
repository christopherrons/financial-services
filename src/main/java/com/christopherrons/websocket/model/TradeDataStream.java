package com.christopherrons.websocket.model;

import com.christopherrons.websocket.api.DataStream;

import java.util.List;

public record TradeDataStream(List<TradeDataStreamItem> tradeDataStreamItems) implements DataStream {

}

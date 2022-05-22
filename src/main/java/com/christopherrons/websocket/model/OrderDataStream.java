package com.christopherrons.websocket.model;

import com.christopherrons.websocket.api.DataStream;

import javax.xml.crypto.Data;
import java.util.List;

public record OrderDataStream(List<OrderDataStreamItem> orderDataStreamItems) implements DataStream {

}

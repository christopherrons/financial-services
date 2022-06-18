package com.christopherrons.marketdataservice.bitstamp.model;

import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BitstampEvent {
    private EventDescriptionEnum eventDescriptionEnum;
    private Map<String, Object> data;
    private String channel;
    private String event;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampEvent(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        this.channel = channel;
        this.data = data;
        this.event = event;
        this.eventDescriptionEnum = EventDescriptionEnum.inferEventDescriptionEnum(event, MarketDataFeedEnum.BITSTAMP);
    }

    public EventDescriptionEnum getEventDescriptionEnum() {
        return eventDescriptionEnum;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getChannel() {
        return channel;
    }

    public String getEvent() {
        return event;
    }

    public BitstampOrder getOrder() {
        return new BitstampOrder(data, channel, event);
    }

    public BitstampTrade getTrade() {
        return new BitstampTrade(data, channel, event);
    }

    public BitstampHeartbeat getHeartBeat() {
        return new BitstampHeartbeat(data, channel, event);
    }

    @Override
    public String toString() {
        return "BitstampEvent{" +
                "eventDescriptionEnum=" + eventDescriptionEnum +
                ", data=" + data +
                ", channel='" + channel + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}

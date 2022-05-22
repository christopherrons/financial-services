package com.christopherrons.websocket.model;

import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;

public record OrderDataStreamItem(double price, double volume, OrderTypeEnum orderTypeEnum) {

}

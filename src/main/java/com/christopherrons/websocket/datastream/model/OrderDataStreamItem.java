package com.christopherrons.websocket.datastream.model;

import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;

public record OrderDataStreamItem(double price,
                                  double volume,
                                  OrderTypeEnum orderTypeEnum,
                                  OrderOperationEnum orderOperationEnum) {

}

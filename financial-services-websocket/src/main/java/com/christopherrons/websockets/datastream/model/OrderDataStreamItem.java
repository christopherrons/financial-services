package com.christopherrons.websockets.datastream.model;

import com.christopherrons.common.enums.marketdata.OrderOperationEnum;
import com.christopherrons.common.enums.marketdata.OrderTypeEnum;

public record OrderDataStreamItem(double price,
                                  double volume,
                                  OrderTypeEnum orderTypeEnum,
                                  OrderOperationEnum orderOperationEnum,
                                  long timeStampMs) {

}

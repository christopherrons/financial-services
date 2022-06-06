package com.christopherrons.websockets.utils;

import com.christopherrons.common.enums.marketdata.OrderOperationEnum;
import com.christopherrons.common.enums.marketdata.OrderTypeEnum;
import com.christopherrons.websockets.datastream.model.OrderDataStreamItem;
import com.christopherrons.websockets.datastream.model.TradeDataStreamItem;

public class DataStreamUtils {

    private DataStreamUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static OrderDataStreamItem createOrderDataStreamItem(final double price,
                                                                final double volume,
                                                                final int orderType,
                                                                final OrderOperationEnum orderOperationEnum) {
        return new OrderDataStreamItem(
                price,
                volume,
                OrderTypeEnum.fromValue(orderType),
                orderOperationEnum
        );
    }

    public static TradeDataStreamItem createTradeDataStreamItem(final double price,
                                                                final double volume,
                                                                final boolean isBidSideAggressor) {
        return new TradeDataStreamItem(
                price,
                volume,
                isBidSideAggressor);
    }
}

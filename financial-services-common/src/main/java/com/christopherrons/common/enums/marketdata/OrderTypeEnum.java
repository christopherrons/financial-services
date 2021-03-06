package com.christopherrons.common.enums.marketdata;


import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum OrderTypeEnum {
    INVALID_ORDER_TYPE(-1),
    BUY(0),
    SELL(1);

    private static final Map<Integer, OrderTypeEnum> VALUES_BY_IDENTIFIER =
            stream(OrderTypeEnum.values()).collect(toMap(OrderTypeEnum::getValue, identity()));
    private final int value;

    OrderTypeEnum(int value) {
        this.value = value;
    }

    public static OrderTypeEnum fromValue(int value) {
        return VALUES_BY_IDENTIFIER.getOrDefault(value, INVALID_ORDER_TYPE);
    }

    public int getValue() {
        return value;
    }
}

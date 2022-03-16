package com.christopherrons.marketdata.common.enums.event;


public enum OrderTypeEnum {
    INVALID_ORDER_TYPE(-1),
    BUY(0),
    SELL(1);

    private final int value;

    OrderTypeEnum(int value) {
        this.value = value;
    }

    public static OrderTypeEnum fromValue(int value) {
        return switch (value) {
            case 0 -> BUY;
            case 1 -> SELL;
            default -> INVALID_ORDER_TYPE;
        };
    }

    public int getValue() {
        return value;
    }
}

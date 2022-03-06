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
        switch (value) {
            case 0:
                return BUY;
            case 1:
                return SELL;
            default:
                return INVALID_ORDER_TYPE;
        }
    }

    public int getValue() {
        return value;
    }
}

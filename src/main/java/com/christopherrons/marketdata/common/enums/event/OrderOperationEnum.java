package com.christopherrons.marketdata.common.enums.event;

public enum OrderOperationEnum {
    INVALID_ORDER_OPERATION("Invalid Order Operation"),
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;

    OrderOperationEnum(String value) {
        this.value = value;
    }

    public static OrderOperationEnum extractValue(String value) {
        for (OrderOperationEnum orderOperationEnum : OrderOperationEnum.values()) {
            if (value.contains(orderOperationEnum.getValue())) {
                return orderOperationEnum;
            }
        }
        return OrderOperationEnum.INVALID_ORDER_OPERATION;
    }

    public OrderOperationEnum fromValue(String value) {
        return switch (value.toLowerCase()) {
            case "create" -> CREATE;
            case "update" -> UPDATE;
            case "delete" -> DELETE;
            default -> INVALID_ORDER_OPERATION;
        };
    }

    public String getValue() {
        return value;
    }
}

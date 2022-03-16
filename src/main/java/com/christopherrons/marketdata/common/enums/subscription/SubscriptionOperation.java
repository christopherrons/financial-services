package com.christopherrons.marketdata.common.enums.subscription;

public enum SubscriptionOperation {
    INVALID_SUBSCRIPTION_OPERATION("Invalid Subscription Operation"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    IS_SUBSCRIBED("is_subscribe");

    private final String value;

    SubscriptionOperation(String value) {
        this.value = value;
    }

    public SubscriptionOperation fromValue(String value) {
        return switch (value.toLowerCase()) {
            case "subscribe" -> SUBSCRIBE;
            case "unsubscribe" -> UNSUBSCRIBE;
            case "is_subscribe" -> IS_SUBSCRIBED;
            default -> INVALID_SUBSCRIPTION_OPERATION;
        };
    }

    public String getValue() {
        return value;
    }
}

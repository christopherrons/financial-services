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
        switch (value.toLowerCase()) {
            case "subscribe":
                return SUBSCRIBE;
            case "unsubscribe":
                return UNSUBSCRIBE;
            case "is_subscribe":
                return IS_SUBSCRIBED;
            default:
                return INVALID_SUBSCRIPTION_OPERATION;
        }
    }

    public String getValue() {
        return value;
    }
}

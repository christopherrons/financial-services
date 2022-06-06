package com.christopherrons.common.enums.marketdata;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum SubscriptionOperation {
    INVALID_SUBSCRIPTION_OPERATION("Invalid Subscription Operation"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    IS_SUBSCRIBED("is_subscribe");

    private static final Map<String, SubscriptionOperation> VALUES_BY_IDENTIFIER =
            stream(SubscriptionOperation.values()).collect(toMap(SubscriptionOperation::getValue, identity()));
    private final String value;

    SubscriptionOperation(String value) {
        this.value = value;
    }

    public SubscriptionOperation fromValue(String value) {
        return VALUES_BY_IDENTIFIER.getOrDefault(value.toLowerCase(), INVALID_SUBSCRIPTION_OPERATION);
    }

    public String getValue() {
        return value;
    }
}

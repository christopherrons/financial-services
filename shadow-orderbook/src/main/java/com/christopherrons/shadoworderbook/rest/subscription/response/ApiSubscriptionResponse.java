package com.christopherrons.shadoworderbook.rest.subscription.response;

public class ApiSubscriptionResponse {

    private boolean isSubscribed;

    public ApiSubscriptionResponse(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}

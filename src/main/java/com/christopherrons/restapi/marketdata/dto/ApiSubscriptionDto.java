package com.christopherrons.restapi.marketdata.dto;

public class ApiSubscriptionDto {

    private boolean isSubscribed;

    public ApiSubscriptionDto(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}

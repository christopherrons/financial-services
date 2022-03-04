package com.christopherrons.restapi.subscription.response;

import java.util.List;

public class ApiAvailableChannelsResponse {

    private List<String> channels;

    public ApiAvailableChannelsResponse(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}

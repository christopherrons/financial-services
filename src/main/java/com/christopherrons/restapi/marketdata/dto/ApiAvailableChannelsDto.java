package com.christopherrons.restapi.marketdata.dto;

import java.util.List;

public class ApiAvailableChannelsDto {

    private List<String> channels;

    public ApiAvailableChannelsDto(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}

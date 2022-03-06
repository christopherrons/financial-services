package com.christopherrons.restapi.refdata.dto;

import java.util.List;

public class ApiAvailableInstrumentsByTypeDto {

    private List<String> instruments;

    public ApiAvailableInstrumentsByTypeDto(List<String> instruments) {
        this.instruments = instruments;
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }
}

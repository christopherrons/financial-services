package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.refdata.instrument.api.Instrument;

import java.util.List;

public class ApiAvailableInstrumentsDto {

    private List<Instrument> instruments;

    public ApiAvailableInstrumentsDto(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }
}

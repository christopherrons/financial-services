package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.refdata.instrument.api.Instrument;

import java.util.List;

public record ApiAvailableInstrumentsDto(List<Instrument> instruments) {

}

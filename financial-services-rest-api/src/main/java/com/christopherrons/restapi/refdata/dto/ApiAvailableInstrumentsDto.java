package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.common.api.refdata.Instrument;

import java.util.List;

public record ApiAvailableInstrumentsDto(List<Instrument> instruments) {

}

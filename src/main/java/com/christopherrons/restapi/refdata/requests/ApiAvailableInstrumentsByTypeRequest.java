package com.christopherrons.restapi.refdata.requests;

import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

public class ApiAvailableInstrumentsByTypeRequest {

    private InstrumentTypeEnum instrumentType;

    public InstrumentTypeEnum getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentTypeEnum instrumentType) {
        this.instrumentType = instrumentType;
    }

    @Override
    public String toString() {
        return "ApiAvailableInstrumentsByType{" +
                "instrumentType='" + instrumentType + '\'' +
                '}';
    }
}

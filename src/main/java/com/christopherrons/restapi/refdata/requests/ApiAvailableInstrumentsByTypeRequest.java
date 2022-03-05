package com.christopherrons.restapi.refdata.requests;

public class ApiAvailableInstrumentsByTypeRequest {

    private String instrumentType;

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    @Override
    public String toString() {
        return "ApiAvailableInstrumentsByType{" +
                "instrumentType='" + instrumentType + '\'' +
                '}';
    }
}

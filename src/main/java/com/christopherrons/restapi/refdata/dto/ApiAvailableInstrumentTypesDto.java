package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.List;

public class ApiAvailableInstrumentTypesDto {

    private List<InstrumentTypeEnum> instrumentTypeEnums;

    public ApiAvailableInstrumentTypesDto(List<InstrumentTypeEnum> instrumentTypeEnums) {
        this.instrumentTypeEnums = instrumentTypeEnums;
    }

    public List<InstrumentTypeEnum> getInstrumentTypeEnums() {
        return instrumentTypeEnums;
    }

    public void setInstrumentTypeEnums(List<InstrumentTypeEnum> instrumentTypeEnums) {
        this.instrumentTypeEnums = instrumentTypeEnums;
    }
}

package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.List;

public record ApiAvailableInstrumentTypesDto(List<InstrumentTypeEnum> instrumentTypeEnums) {
}

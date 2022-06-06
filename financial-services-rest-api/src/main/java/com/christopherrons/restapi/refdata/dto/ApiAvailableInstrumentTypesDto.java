package com.christopherrons.restapi.refdata.dto;

import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

import java.util.List;

public record ApiAvailableInstrumentTypesDto(List<InstrumentTypeEnum> instrumentTypeEnums) {
}

package com.christopherrons.restapi.refdata.service;

import com.christopherrons.refdata.RefDataService;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentTypesDto;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentsByTypeDto;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentsDto;
import com.christopherrons.restapi.refdata.requests.util.RefDataRequestValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefDataApiService {

    @Autowired
    private RefDataService refDataService;

    public ApiAvailableInstrumentsDto getAvailableInstruments() {
        return new ApiAvailableInstrumentsDto(refDataService.getInstruments());
    }

    public ApiAvailableInstrumentTypesDto getAvailableInstrumentTypes() {
        return new ApiAvailableInstrumentTypesDto(InstrumentTypeEnum.getInstrumentType());
    }

    public ApiAvailableInstrumentsByTypeDto getAvailableInstrumentsByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return new ApiAvailableInstrumentsByTypeDto(refDataService.getInstrumentsByType(instrumentTypeEnum));
    }
}

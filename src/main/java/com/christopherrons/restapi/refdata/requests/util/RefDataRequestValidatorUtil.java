package com.christopherrons.restapi.refdata.requests.util;

import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.restapi.refdata.exceptions.ApiInstrumentsByTypeRequestException;

public class RefDataRequestValidatorUtil {

    private RefDataRequestValidatorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static InstrumentTypeEnum extractAndValidateInstrumentType(final String instrumentTypeName) {
        InstrumentTypeEnum instrumentTypeEnum = InstrumentTypeEnum.fromName(instrumentTypeName);
        if (instrumentTypeEnum.equals(InstrumentTypeEnum.INVALID_INSTRUMENT)) {
            throw (new ApiInstrumentsByTypeRequestException(instrumentTypeEnum.getName(), InstrumentTypeEnum.getInstrumentTypeNames()));
        }
        return instrumentTypeEnum;
    }
}

package com.christopherrons.refdata.instrument.api;

import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

public interface Derivative extends Instrument {

    @Override
    default InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.DERIVATIVE;
    }

    long getTimeToMaturity();
}

package com.christopherrons.refdata.api;

import com.christopherrons.refdata.enums.InstrumentTypeEnum;

public interface Derivative extends Instrument {

    @Override
    default InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.DERIVATIVE;
    }

    long getTimeToMaturity();
}

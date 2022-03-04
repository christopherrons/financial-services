package com.christopherrons.refdata.api;

import com.christopherrons.refdata.enums.InstrumentType;

public interface Derivative extends Instrument {

    @Override
    default InstrumentType getInstrumentType() {
        return InstrumentType.DERIVATIVE;
    }

    long getTimeToMaturity();
}

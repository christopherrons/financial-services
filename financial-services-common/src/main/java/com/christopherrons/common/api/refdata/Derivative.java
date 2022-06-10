package com.christopherrons.common.api.refdata;

import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

public interface Derivative extends Instrument {

    @Override
    default InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.DERIVATIVE;
    }

    long getTimeToMaturity();
}

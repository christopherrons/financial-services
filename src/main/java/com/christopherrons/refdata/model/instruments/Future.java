package com.christopherrons.refdata.model.instruments;

import com.christopherrons.refdata.api.Derivative;
import com.christopherrons.refdata.enums.InstrumentType;

public class Future implements Derivative {
    private final String tradingPair;

    public Future(String tradingPair) {
        this.tradingPair = tradingPair;
    }

    @Override
    public String getTradingPair() {
        return tradingPair;
    }

    @Override
    public long getTimeToMaturity() {
        return 0;
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.FUTURE;
    }
}

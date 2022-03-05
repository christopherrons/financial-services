package com.christopherrons.refdata.model.instruments;

import com.christopherrons.refdata.api.Derivative;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;

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
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.FUTURE;
    }
}